package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartMenu extends BorderPane {
  public static final String DEFAULT_CSS = ScreenCreator.RESOURCES.replace(".", "/")
          + "default.css";

  public StartMenu(EventHandler<ActionEvent> startAction) {
    this.getStylesheets().add(DEFAULT_CSS);
    VBox center = new VBox();
    center.getStyleClass().add("start-screen");
    Label title = new Label("PikaCoding");
    title.getStyleClass().add("title");

    Button startButton = new Button("Start Game");
    startButton.setOnAction(startAction);
    startButton.setId("start-button");
    //BorderPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
    center.getChildren().addAll(title, startButton);
    this.setCenter(center);
  }

}
