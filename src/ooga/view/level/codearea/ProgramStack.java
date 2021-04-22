package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  private int newIndex = 0;

  public ProgramStack() {
    this.setId("program-stack");
    this.setSpacing(5);
    programBlocks = new ArrayList<>();
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    this.availableCommands = availableCommands;
  }

  public void addCommandBlock(String command) {
    List<Map<String, List<String>>> parameterOptions = new ArrayList<>();
    availableCommands.getParameters(command).forEach(parameter -> {
      Map<String, List<String>> parameterOptionsMap = new HashMap<>();
      parameterOptionsMap.put(parameter, availableCommands.getParameterOptions(command, parameter));
      parameterOptions.add(parameterOptionsMap);
    });
    if (command.equals("jump")) {
      CommandBlockHolder commandBlockHolder = new JumpCommandBlockHolder(programBlocks.size() + 1,
          command, parameterOptions, this);
      programBlocks.add(commandBlockHolder);
      this.getChildren().add(commandBlockHolder);
    } else if (command.equals("if")) {
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
      this.getChildren().addAll(beginCommandBlockHolder, endCommandBlockHolder);
    } else {
      CommandBlockHolder commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
          command, parameterOptions, this);
      programBlocks.add(commandBlockHolder);
      this.getChildren().add(commandBlockHolder);
    }
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
        if (commandBlockHolder instanceof NestedBeginBlockHolder) {
          if (newIndex < ((NestedBeginBlockHolder) commandBlockHolder).getEndCommandBlockHolder().getIndex()) {
            moveCommandBlock(commandBlockHolder.getCommandBlock().getIndex(), newIndex);
          }
        }
        else if (commandBlockHolder instanceof NestedEndBlockHolder) {
          if (newIndex > ((NestedEndBlockHolder) commandBlockHolder).getBeginCommandBlockHolder().getIndex()) {
            moveCommandBlock(commandBlockHolder.getCommandBlock().getIndex(), newIndex);
          }
        }
        else {
          moveCommandBlock(commandBlockHolder.getCommandBlock().getIndex(), newIndex);;
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

}
