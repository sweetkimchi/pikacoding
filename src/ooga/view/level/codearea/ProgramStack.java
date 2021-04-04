package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;

public class ProgramStack extends VBox {

  private List<CommandBlockHolder> programBlocks;

  public ProgramStack() {
    programBlocks = new ArrayList<>();
  }

  public void addCommandBlock(String command) {
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(programBlocks.size() + 1,
        command,
        this::removeCommandBlock);
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
