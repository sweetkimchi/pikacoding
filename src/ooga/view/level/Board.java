package ooga.view.level;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Board extends StackPane {

  public Board() {
    this.getChildren().add(new Label("Board"));
  }

}
