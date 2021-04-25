package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameTypeSelector extends BorderPane {

  public GameTypeSelector(EventHandler<ActionEvent> singleAction, EventHandler<ActionEvent> multiAction) {
    HBox gameType = new HBox();
    gameType.getStyleClass().add("type-screen");

    Button singlePlayer = new Button("Single Player");
    singlePlayer.getStyleClass().add("default-button");
    singlePlayer.setOnAction(singleAction);

    Button multiPlayer = new Button("Multiplayer");
    multiPlayer.getStyleClass().add("default-button");
    multiPlayer.setOnAction(multiAction);

    gameType.getChildren().addAll(singlePlayer, multiPlayer);

    Label instruction = new Label("Choose Game Mode");
    instruction.getStyleClass().add("title");

    VBox iBox = new VBox();
    iBox.getChildren().addAll(instruction, gameType);
    iBox.getStyleClass().add("instruction-box");

    this.setCenter(iBox);
  }
}
