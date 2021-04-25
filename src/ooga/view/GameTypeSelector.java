package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class GameTypeSelector extends BorderPane {

  private static final String GAME_TYPE_STRINGS = ScreenCreator.RESOURCES + "GameTypeSelector";

  public GameTypeSelector(EventHandler<ActionEvent> singleAction, EventHandler<ActionEvent> multiAction) {
    ResourceBundle gameTypeResources = ResourceBundle.getBundle(GAME_TYPE_STRINGS);
    HBox gameType = new HBox();
    gameType.getStyleClass().add("type-screen");

    Button singlePlayer = new Button(gameTypeResources.getString("singlePlayer"));
    singlePlayer.getStyleClass().add("default-button");
    singlePlayer.setOnAction(singleAction);

    Button multiPlayer = new Button(gameTypeResources.getString("multiPlayer"));
    multiPlayer.getStyleClass().add("default-button");
    multiPlayer.setOnAction(multiAction);

    gameType.getChildren().addAll(singlePlayer, multiPlayer);

    Label instruction = new Label(gameTypeResources.getString("instructions"));
    instruction.getStyleClass().add("title");

    VBox iBox = new VBox();
    iBox.getChildren().addAll(instruction, gameType);
    iBox.getStyleClass().add("instruction-box");

    this.setCenter(iBox);
  }
}
