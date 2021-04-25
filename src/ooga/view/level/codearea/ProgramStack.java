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

  private List<CommandBlockHolder> programBlocks;
  private AvailableCommands availableCommands;
  private AvailableCommands availableCommandsOtherPlayer;
  private List<ProgramListener> programListeners;

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

  public void addCommandBlock(String command) {
//    System.out.println(command + " 1");
    System.out.println(command + " " + availableCommands.getCommandNames());
    if (availableCommands.getCommandNames().contains(command)) {
      createAndAddCommandBlock(availableCommands, command, false);
    } else if (availableCommandsOtherPlayer.getCommandNames().contains(command)) {
      createAndAddCommandBlock(availableCommandsOtherPlayer, command, true);
    }
//    System.out.println(command + " 2");
    for (int i = 0; i < programBlocks.size(); i++) {
      CommandBlockHolder commandBlockHolder = programBlocks.get(i);
      if (commandBlockHolder instanceof JumpCommandBlockHolder) {
        ((JumpCommandBlockHolder) commandBlockHolder).updateDropdown(programBlocks.size());
      }
    }
//    System.out.println(command + " 3");
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
    for (int i = 0; i < programBlocks.size(); i++) {
      CommandBlockHolder commandBlockHolder = programBlocks.get(i);
      if (commandBlockHolder instanceof JumpCommandBlockHolder) {
        ((JumpCommandBlockHolder) commandBlockHolder).updateDropdown(programBlocks.size());
      }
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
//    List<CommandBlock> programCopy = new ArrayList<>();
//    for (CommandBlock commandBlock : program) {
//      programCopy.add(new CommandBlock(commandBlock.getIndex(), commandBlock.getType(),
//          commandBlock.getParameters()));
//    }

//    System.out.println("program recieved " + program.size());
    Platform.runLater(() -> {
      programBlocks.clear();
      this.getChildren().clear();
    });

    Platform.runLater(() -> {
      for (int i = 0; i < program.size(); i++) {
        CommandBlock commandBlock = program.get(i);
//      System.out.println(programCopy.size());
        addCommandBlock(commandBlock.getType());
        if (commandBlock.getParameters() != null) {
//          System.out.println(commandBlock.getParameters().keySet());
          for (String parameter : commandBlock.getParameters().keySet()) {
            String option = commandBlock.getParameters().get(parameter);
//            System.out.println(parameter + option);
            if (programBlocks.get(programBlocks.size() - 1) instanceof NestedEndBlockHolder) {
              programBlocks.get(programBlocks.size() - 2)
                  .selectParameter(parameter, option);
            }
            else {
              programBlocks.get(programBlocks.size() - 1)
                  .selectParameter(parameter, option);
            }
          }
        }
      }
    });

//      commandBlock.getParameters().forEach(
//          (parameter, option) -> {
//            System.out.println(parameter + option);
//            programBlocks.get(programBlocks.size() - 1)
//              .selectParameter(parameter, option);});
//    System.out.println("paratmeter");
  }
//    System.out.println(programCopy.size());

  private void createAndAddCommandBlock(AvailableCommands availableCommands, String command,
      boolean isMultiplayer) {
    List<Map<String, List<String>>> parameterOptions = new ArrayList<>();
    availableCommands.getParameters(command).forEach(parameter -> {
      Map<String, List<String>> parameterOptionsMap = new HashMap<>();
      parameterOptionsMap
          .put(parameter, availableCommands.getParameterOptions(command, parameter));
      parameterOptions.add(parameterOptionsMap);
    });
    if (command.equals("jump")) {
      addJumpCommandBlock(command, parameterOptions, isMultiplayer);
    } else if (command.equals("if")) {
      addNestedCommandBlocks(command, parameterOptions, isMultiplayer);
    } else {
      addStandardCommandBlock(command, parameterOptions, isMultiplayer);
    }
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

  private void addStandardCommandBlock(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isMultiplayer) {
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
        command, parameterOptions, this);
    if (isMultiplayer) {
      commandBlockHolder.setOtherPlayer();
    }
    programBlocks.add(commandBlockHolder);
    Platform.runLater(() -> this.getChildren().add(commandBlockHolder));
  }

  private void addNestedCommandBlocks(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isMultiplayer) {
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
    if (isMultiplayer) {
      beginCommandBlockHolder.setOtherPlayer();
      endCommandBlockHolder.setOtherPlayer();
    }
    Platform.runLater(() -> this.getChildren().addAll(beginCommandBlockHolder, endCommandBlockHolder));
  }

  private void addJumpCommandBlock(String command,
      List<Map<String, List<String>>> parameterOptions, boolean isMultiplayer) {
    CommandBlockHolder commandBlockHolder = new JumpCommandBlockHolder(programBlocks.size() + 1,
        command, parameterOptions, this);
    if (isMultiplayer) {
      commandBlockHolder.setOtherPlayer();
    }
    programBlocks.add(commandBlockHolder);
    Platform.runLater(() -> this.getChildren().add(commandBlockHolder));
  }

}
