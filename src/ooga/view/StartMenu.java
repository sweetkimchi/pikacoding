package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class StartMenu extends BorderPane {

  public StartMenu(EventHandler<ActionEvent> startAction) {
    Label title = new Label("Title");
    this.setCenter(title);

    Button startButton = new Button("Start Game");
    startButton.setOnAction(startAction);
    BorderPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
    this.setBottom(startButton);
  }

}
