package ooga.view.level.codearea;

import javafx.scene.layout.VBox;

public class ProgramStack extends VBox {

  public void addCommandBlock(String command) {
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(this.getChildren().size(),
        command,
        this::removeCommandBlock);
    this.getChildren().add(commandBlockHolder);
  }

  public void removeCommandBlock(int index) {
    this.getChildren().remove(index);
  }
}
