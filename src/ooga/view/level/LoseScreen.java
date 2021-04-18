package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoseScreen extends VBox {

  public LoseScreen(EventHandler<ActionEvent> tryAgainAction) {
    this.getStyleClass().add("start-screen");
    Label loseMessage = new Label("Pikachu ran out of apples!");
    loseMessage.getStyleClass().add("title");
    this.getChildren().add(loseMessage);
    Button tryAgainButton = new Button("Try Again");
    tryAgainButton.setOnAction(tryAgainAction);
    this.getChildren().add(tryAgainButton);
  }

}
