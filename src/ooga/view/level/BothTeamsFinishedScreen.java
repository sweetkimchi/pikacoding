package ooga.view.level;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;

public class BothTeamsFinishedScreen extends VBox {

  public static final String SCREEN_MESSAGES = "ScreenStrings";

  private ResourceBundle winMessages;

  public BothTeamsFinishedScreen(int currentScore, int otherScore,
      EventHandler<ActionEvent> homeAction, EventHandler<ActionEvent> playAgainAction) {
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label resultMessage = new Label();
    if (currentScore > otherScore) {
      resultMessage.setText(winMessages.getString("teamWin"));
    }
    else if (currentScore < otherScore) {
      resultMessage.setText(winMessages.getString("teamLose"));
    }
    else {
      resultMessage.setText(winMessages.getString("teamTie"));
    }
    resultMessage.getStyleClass().add("title");
    this.getChildren().add(resultMessage);
    Label currentScoreMessage = new Label(getScore(currentScore, "currentScore"));
    this.getChildren().add(currentScoreMessage);
    Label otherScoreMessage = new Label(getScore(otherScore, "otherScore"));
    this.getChildren().add(otherScoreMessage);

    Button homeButton = new Button(winMessages.getString("home"));
    homeButton.setOnAction(homeAction);
    this.getChildren().add(homeButton);

    Button playAgainButton = new Button(winMessages.getString("playAgain"));
    playAgainButton.setOnAction(playAgainAction);
    this.getChildren().add(playAgainButton);
  }

  private String getScore(int score, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = score;
    formatter.applyPattern(winMessages.getString(key));
    return formatter.format(currNum);
  }
}
