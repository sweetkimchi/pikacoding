package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WinScreen extends VBox {

  public WinScreen(int score, EventHandler<ActionEvent> homeAction, EventHandler<ActionEvent> nextLevelAction) {
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label("You win!");
    winMessage.getStyleClass().add("title");
    Label scoreMessage = new Label("Your score was: " + score);
    Button nextLevelButton = new Button("Next level");
    nextLevelButton.setOnAction(nextLevelAction);
    Button homeButton = new Button("Home");
    homeButton.setOnAction(homeAction);

    this.getChildren().addAll(winMessage, scoreMessage, nextLevelButton, homeButton);
  }

}
