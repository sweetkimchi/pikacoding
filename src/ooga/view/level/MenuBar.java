package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MenuBar extends HBox {

  public MenuBar(EventHandler<ActionEvent> pauseAction) {
    Button pauseButton = new Button("Pause");
    pauseButton.setOnAction(pauseAction);
    this.getChildren().add(pauseButton);
  }

}
