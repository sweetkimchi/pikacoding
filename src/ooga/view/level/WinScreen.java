package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WinScreen extends VBox {

  public WinScreen(int score, EventHandler<ActionEvent> homeAction,
      EventHandler<ActionEvent> nextLevelAction, boolean isLastLevel) {
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label("You win!");
    winMessage.getStyleClass().add("title");
    this.getChildren().add(winMessage);
    Label scoreMessage = new Label("Your score was: " + score);
    this.getChildren().add(scoreMessage);
    if (!isLastLevel) {
      Button nextLevelButton = new Button("Next level");
      nextLevelButton.setOnAction(nextLevelAction);
      this.getChildren().add(nextLevelButton);
    }
    Button homeButton = new Button("Home");
    homeButton.setOnAction(homeAction);
    this.getChildren().add(homeButton);
  }

}
