package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;

import java.util.ResourceBundle;

public class GameTypeSelector extends BorderPane {

  private static final String GAME_TYPE_STRINGS = ScreenCreator.RESOURCES + "GameTypeSelector";
  private static final int NO_NUM = 0;

  private GUIElementInterface GUIFactory;

  public GameTypeSelector(EventHandler<ActionEvent> singleAction, EventHandler<ActionEvent> multiAction) {
    GUIFactory = new GUIElementFactory();
    ResourceBundle gameTypeResources = ResourceBundle.getBundle(GAME_TYPE_STRINGS);
    HBox gameType = new HBox();
    gameType.getStyleClass().add("type-screen");

    Button singlePlayer = GUIFactory.makeButton(gameTypeResources, singleAction, "singlePlayer", "default-button", "singlePlayer", NO_NUM);
    Button multiPlayer = GUIFactory.makeButton(gameTypeResources, multiAction, "multiPlayer", "default-button", "multiPlayer", NO_NUM);
    gameType.getChildren().addAll(singlePlayer, multiPlayer);

    Label instruction = GUIFactory.makeLabel(gameTypeResources, "instructions", "title", NO_NUM);
            new Label(gameTypeResources.getString("instructions"));

    VBox iBox = new VBox();
    iBox.getChildren().addAll(instruction, gameType);
    iBox.getStyleClass().add("instruction-box");

    this.setCenter(iBox);
  }
}
