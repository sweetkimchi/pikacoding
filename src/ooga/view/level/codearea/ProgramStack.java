package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ooga.model.commands.AvailableCommands;

/**
 * Displays the player-created program comprised of command blocks.
 *
 * @author David Li
 */
public class ProgramStack extends VBox {

  private final List<CommandBlockHolder> programBlocks;
  private AvailableCommands availableCommands;
  private AvailableCommands availableCommandsOtherPlayer;
  private final List<ProgramListener> programListeners;

  private int newIndex = 0;

  public ProgramStack() {
    this.setId("program-stack");
    this.setSpacing(5);
    programBlocks = new ArrayList<>();
    programListeners = new ArrayList<>();
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    this.availableCommands = availableCommands;
  }

  public void setAvailableCommandsOtherPlayer(AvailableCommands availableCommands) {
    this.availableCommandsOtherPlayer = availableCommands;
  }

  public CommandBlockHolder addCommandBlock(String command) {
    CommandBlockHolder commandBlockHolder = null;
    if (availableCommands.getCommandNames().contains(command)) {
      commandBlockHolder = createAndAddCommandBlock(availableCommands, command, false);
    } else if (availableCommandsOtherPlayer.getCommandNames().contains(command)) {
      commandBlockHolder = createAndAddCommandBlock(availableCommandsOtherPlayer, command, true);
    }
    return commandBlockHolder;
  }

  public List<CommandBlock> getProgram() {
    List<CommandBlock> program = new ArrayList<>();
    programBlocks.forEach(commandBlockHolder -> program.add(commandBlockHolder.getCommandBlock()));
    return program;
  }

  public void removeCommandBlock(int index) {
    programBlocks.remove(index - 1);
    this.getChildren().remove(index - 1);
    for (int i = index - 1; i < programBlocks.size(); i++) {
      programBlocks.get(i).setIndex(i + 1);
    }
  }

  public void startMove(CommandBlockHolder commandBlockHolder) {
    newIndex = commandBlockHolder.getCommandBlock().getIndex();
    commandBlockHolder.getStyleClass().add("command-block-selected");
    programBlocks.forEach(other -> {
      other.setButtonsDisabled(true);
      other.setOnMouseEntered(e -> {
        other.getStyleClass().add("command-block-hovered");
        newIndex = other.getCommandBlock().getIndex();
      });
      other.setOnMouseExited(e -> {
        other.getStyleClass().remove("command-block-hovered");
      });
      other.setOnMouseClicked(e -> {
        if (canBeMoved(commandBlockHolder, newIndex)) {
          moveCommandBlock(commandBlockHolder.getCommandBlock().getIndex(), newIndex);
          notifyProgramListeners();
        }
        resetMouseActions();
      });
    });
  }

  public void setLineIndicators(Map<Integer, Integer> lineNumbers) {
    List<List<Integer>> indicators = new ArrayList<>();
    programBlocks.forEach(commandBlockHolder -> indicators.add(new ArrayList<>()));
    lineNumbers.forEach((id, lineNumber) -> indicators.get(lineNumber - 1).add(id));
    for (int i = 0; i < programBlocks.size(); i++) {
      programBlocks.get(i).setLineIndicators(indicators.get(i));
    }
  }

  public void addProgramListener(ProgramListener programListener) {
    programListeners.add(programListener);
  }

  public void notifyProgramListeners() {
    programListeners.forEach(ProgramListener::onProgramUpdate);
  }

  public void receiveProgramUpdates(List<CommandBlock> program) {
    Platform.runLater(() -> {
      programBlocks.clear();
      this.getChildren().clear();
    });

    Platform.runLater(() -> {
      for (CommandBlock commandBlock : program) {
        CommandBlockHolder commandBlockHolder = addCommandBlockFromDatabase(commandBlock.getType());
        if (commandBlock.getParameters() != null) {
          for (String parameter : commandBlock.getParameters().keySet()) {
            String option = commandBlock.getParameters().get(parameter);
            if (commandBlockHolder instanceof NestedBeginBlockHolder) {
              int size = programBlocks.size();
              programBlocks.remove(size - 1);
              Platform.runLater(() -> this.getChildren().remove(size - 1));
            }
            commandBlockHolder.selectParameter(parameter, option);
          }
        }
      }
    });
  }

  private CommandBlockHolder createAndAddCommandBlock(AvailableCommands availableCommands, String command,
      boolean isOtherPlayerCommandBlock) {
    List<Map<String, List<String>>> parameterOptions = new ArrayList<>();
    if (isOtherPlayerCommandBlock) {
      availableCommandsOtherPlayer.getParameters(command).forEach(parameter -> {
        Map<String, List<String>> parameterOptionsMap = new HashMap<>();
        parameterOptionsMap
            .put(parameter, availableCommandsOtherPlayer.getParameterOptions(command, parameter));
        parameterOptions.add(parameterOptionsMap);
      });
    } else {
      availableCommands.getParameters(command).forEach(parameter -> {
        Map<String, List<String>> parameterOptionsMap = new HashMap<>();
        parameterOptionsMap
            .put(parameter, availableCommands.getParameterOptions(command, parameter));
        parameterOptions.add(parameterOptionsMap);
      });
    }
    CommandBlockHolder commandBlockHolder;
    if (command.equals("jump")) {
      commandBlockHolder = addJumpCommandBlock(command, parameterOptions, isOtherPlayerCommandBlock);
    } else if (command.equals("if")) {
      commandBlockHolder = addNestedCommandBlocks(command, parameterOptions, isOtherPlayerCommandBlock);
    } else {
      commandBlockHolder = addStandardCommandBlock(command, parameterOptions, isOtherPlayerCommandBlock);
    }
    return commandBlockHolder;
  }

  private CommandBlockHolder createAndAddCommandBlockFromDatabase(AvailableCommands availableCommands,
      String command, boolean isNestedEnd, boolean isOtherPlayerCommandBlock) {
    CommandBlockHolder commandBlockHolder;
    if (isNestedEnd) {
      commandBlockHolder = addStandardCommandBlock(command, new ArrayList<>(), isOtherPlayerCommandBlock);
    } else {
      commandBlockHolder = createAndAddCommandBlock(availableCommands, command, isOtherPlayerCommandBlock);
    }
    return commandBlockHolder;
  }

  private void moveCommandBlock(int oldIndex, int newIndex) {
    if (oldIndex < newIndex) {
      Collections.rotate(programBlocks.subList(oldIndex - 1, newIndex), -1);
    } else if (oldIndex > newIndex) {
      Collections.rotate(programBlocks.subList(newIndex - 1, oldIndex), 1);
    }
    this.getChildren().clear();
    for (int i = 0; i < programBlocks.size(); i++) {
      programBlocks.get(i).setIndex(i + 1);
      this.getChildren().add(programBlocks.get(i));
    }
  }

  private CommandBlockHolder addStandardCommandBlock(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isOtherPlayerCommandBlock) {
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
        command, parameterOptions, this);
    if (isOtherPlayerCommandBlock) {
      commandBlockHolder.setOtherPlayer();
    }
    programBlocks.add(commandBlockHolder);
    Platform.runLater(() -> this.getChildren().add(commandBlockHolder));
    return commandBlockHolder;
  }

  private CommandBlockHolder addNestedCommandBlocks(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isOtherPlayerCommandBlock) {
    NestedBeginBlockHolder beginCommandBlockHolder = new NestedBeginBlockHolder(
        programBlocks.size() + 1,
        command, parameterOptions, this);
    programBlocks.add(beginCommandBlockHolder);
    NestedEndBlockHolder endCommandBlockHolder = new NestedEndBlockHolder(
        programBlocks.size() + 1,
        command, this);
    programBlocks.add(endCommandBlockHolder);
    beginCommandBlockHolder.attachEndHolder(endCommandBlockHolder);
    endCommandBlockHolder.attachBeginHolder(beginCommandBlockHolder);
    if (isOtherPlayerCommandBlock) {
      beginCommandBlockHolder.setOtherPlayer();
      endCommandBlockHolder.setOtherPlayer();
    }
    Platform
        .runLater(() -> this.getChildren().addAll(beginCommandBlockHolder, endCommandBlockHolder));
    return beginCommandBlockHolder;
  }

  private CommandBlockHolder addJumpCommandBlock(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isOtherPlayerCommandBlock) {
    CommandBlockHolder commandBlockHolder = new JumpCommandBlockHolder(programBlocks.size() + 1,
        command, parameterOptions, this);
    if (isOtherPlayerCommandBlock) {
      commandBlockHolder.setOtherPlayer();
    }
    programBlocks.add(commandBlockHolder);
    Platform.runLater(() -> this.getChildren().add(commandBlockHolder));
    return commandBlockHolder;
  }

  private CommandBlockHolder addCommandBlockFromDatabase(String command) {
    CommandBlockHolder commandBlockHolder = null;
    if (command.startsWith("end ")) {
      if (availableCommands.getCommandNames().contains(command.substring(4))) {
        commandBlockHolder = createAndAddCommandBlockFromDatabase(availableCommands, command, true, false);
      } else if (availableCommandsOtherPlayer.getCommandNames().contains(command.substring(4))) {
        commandBlockHolder = createAndAddCommandBlockFromDatabase(availableCommandsOtherPlayer, command, true, true);
      }
    } else {
      if (availableCommands.getCommandNames().contains(command)) {
        commandBlockHolder = createAndAddCommandBlockFromDatabase(availableCommands, command, false, false);
      } else if (availableCommandsOtherPlayer.getCommandNames().contains(command)) {
        commandBlockHolder = createAndAddCommandBlockFromDatabase(availableCommandsOtherPlayer, command, false, true);
      }
    }
    return commandBlockHolder;
  }

  private void resetMouseActions() {
    programBlocks.forEach(commandBlockHolder -> {
      commandBlockHolder.getStyleClass().remove("command-block-selected");
      commandBlockHolder.getStyleClass().remove("command-block-hovered");
      commandBlockHolder.setButtonsDisabled(false);
      commandBlockHolder.setOnMouseEntered(e -> {
      });
      commandBlockHolder.setOnMouseClicked(e -> {
      });
    });
  }

  private boolean canBeMoved(CommandBlockHolder commandBlockHolder, int newIndex) {
    if (commandBlockHolder instanceof NestedBeginBlockHolder) {
      return newIndex < ((NestedBeginBlockHolder) commandBlockHolder).getEndCommandBlockHolder()
          .getIndex();
    } else if (commandBlockHolder instanceof NestedEndBlockHolder) {
      return newIndex > ((NestedEndBlockHolder) commandBlockHolder).getBeginCommandBlockHolder()
          .getIndex();
    }
    return true;
  }

}
