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

  private boolean awaitingNewIndex = false;
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
    CommandBlockHolder commandBlockHolder;
    if (command.equals("jump")) {
      commandBlockHolder = new JumpCommandBlockHolder(programBlocks.size() + 1,
          command, parameterOptions, this);
    }
    else {
      commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
          command, parameterOptions, this);
    }
    programBlocks.add(commandBlockHolder);
    this.getChildren().add(commandBlockHolder);
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

  public void startDrag(CommandBlockHolder commandBlockHolder) {
    newIndex = commandBlockHolder.getCommandBlock().getIndex();
    awaitingNewIndex = true;
    programBlocks.forEach(other -> {
      other.setOnMouseEntered(e -> {
        if (awaitingNewIndex) {
          newIndex = other.getCommandBlock().getIndex();
          moveCommandBlock(commandBlockHolder.getCommandBlock().getIndex(), newIndex);
          stopDrag();
        }
      });
    });
  }

  private void moveCommandBlock(int oldIndex, int newIndex) {
    if (oldIndex < newIndex) {
      Collections.rotate(programBlocks.subList(oldIndex - 1, newIndex), -1);
    }
    else if (oldIndex > newIndex) {
      Collections.rotate(programBlocks.subList(newIndex - 1, oldIndex), 1);
    }
    this.getChildren().clear();
    for (int i = 0; i < programBlocks.size(); i++) {
      programBlocks.get(i).setIndex(i + 1);
      this.getChildren().add(programBlocks.get(i));
    }
  }

  private void stopDrag() {
    programBlocks.forEach(other -> {
      other.setOnMouseEntered(e -> {});
    });
    awaitingNewIndex = false;
  }
}
