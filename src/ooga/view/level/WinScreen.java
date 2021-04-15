package ooga.view.level;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WinScreen extends VBox {

  public WinScreen(int score) {
    Label winMessage = new Label("You win!");
    Label scoreMessage = new Label("Your score was: " + score);
    Button nextLevelButton = new Button("Next level");
    Button homeButton = new Button("Home");

    this.getChildren().addAll(winMessage, scoreMessage, nextLevelButton, homeButton);
  }

}
