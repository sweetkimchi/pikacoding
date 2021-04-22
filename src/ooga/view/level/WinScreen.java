package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class WinScreen extends VBox {
  public static final String SCREEN_MESSAGES = "ScreenStrings";

  private ResourceBundle winMessages;

  public WinScreen(int score, EventHandler<ActionEvent> homeAction,
      EventHandler<ActionEvent> nextLevelAction, boolean isLastLevel) {
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label(winMessages.getString("win"));
    winMessage.getStyleClass().add("title");
    this.getChildren().add(winMessage);
    Label scoreMessage = new Label(getScore(score, "winScore"));
    this.getChildren().add(scoreMessage);
    if (!isLastLevel) {
      Button nextLevelButton = new Button(winMessages.getString("next"));
      nextLevelButton.setOnAction(nextLevelAction);
      this.getChildren().add(nextLevelButton);
    }
    Button homeButton = new Button(winMessages.getString("home"));
    homeButton.setOnAction(homeAction);
    this.getChildren().add(homeButton);
  }

  private String getScore(int score, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = score;
    formatter.applyPattern(winMessages.getString(key));
    return formatter.format(currNum);
  }

}
