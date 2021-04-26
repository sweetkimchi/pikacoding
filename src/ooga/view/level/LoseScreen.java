package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;

import java.util.ResourceBundle;

public class LoseScreen extends VBox {

  public LoseScreen(EventHandler<ActionEvent> tryAgainAction) {
    ResourceBundle loseMessages = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + WinScreen.SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label loseMessage = new Label(loseMessages.getString("lose"));
    loseMessage.getStyleClass().add("title");
    this.getChildren().add(loseMessage);
    Button tryAgainButton = new Button(loseMessages.getString("loseButton"));
    tryAgainButton.setId(ScreenCreator.idsForTests.getString("tryAgain"));
    tryAgainButton.setOnAction(tryAgainAction);
    this.getChildren().add(tryAgainButton);
  }

}
