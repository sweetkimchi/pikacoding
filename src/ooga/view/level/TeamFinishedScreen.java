package ooga.view.level;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;

public class TeamFinishedScreen extends VBox {

  public static final String SCREEN_MESSAGES = "ScreenStrings";

  private ResourceBundle winMessages;

  public TeamFinishedScreen(int score) {
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label(winMessages.getString("teamFinished"));
    winMessage.getStyleClass().add("title");
    this.getChildren().add(winMessage);
    Label scoreMessage = new Label(getScore(score, "winScore"));
    this.getChildren().add(scoreMessage);
    Label waitingMessage = new Label(winMessages.getString("waitingOtherTeam"));
    this.getChildren().add(waitingMessage);
  }

  private String getScore(int score, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = score;
    formatter.applyPattern(winMessages.getString(key));
    return formatter.format(currNum);
  }

}
