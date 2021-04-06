package ooga.view.level.codearea;

import java.util.ArrayList;
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

  public ProgramStack() {
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
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
        command, parameterOptions, this::removeCommandBlock);
    programBlocks.add(commandBlockHolder);
    this.getChildren().add(commandBlockHolder);
  }

  public void removeCommandBlock(int index) {
    programBlocks.remove(index - 1);
    this.getChildren().remove(index - 1);
    for (int i = index - 1; i < programBlocks.size(); i++) {
      programBlocks.get(i).setIndex(i + 1);
    }
  }

  public List<CommandBlock> getProgram() {
    List<CommandBlock> program = new ArrayList<>();
    programBlocks.forEach(commandBlockHolder -> program.add(commandBlockHolder.getCommandBlock()));
    return program;
  }
}
