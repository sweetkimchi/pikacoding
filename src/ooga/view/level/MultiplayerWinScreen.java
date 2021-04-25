package ooga.view.level;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;

public class MultiplayerWinScreen extends VBox {

  public static final String SCREEN_MESSAGES = "ScreenStrings";

  private ResourceBundle winMessages;
  private Label scoreMessage;

  public MultiplayerWinScreen(EventHandler<ActionEvent> homeAction,
      EventHandler<ActionEvent> goBackAction, EventHandler<ActionEvent> finishedAction) {
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label(winMessages.getString("win"));
    winMessage.getStyleClass().add("title");
    this.getChildren().add(winMessage);

    scoreMessage = new Label();
    this.getChildren().add(scoreMessage);

    Button goBackButton = new Button(winMessages.getString("goBack"));
    goBackButton.setOnAction(goBackAction);
    this.getChildren().add(goBackButton);

    Button finishedButton = new Button(winMessages.getString("finished"));
    finishedButton.setOnAction(finishedAction);
    this.getChildren().add(finishedButton);

    Button homeButton = new Button(winMessages.getString("home"));
    homeButton.setOnAction(homeAction);
    this.getChildren().add(homeButton);
  }

  public void setScores(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    scoreMessage.setText(
        getScore(executionScore, "executionScore") + "\n" + getScore(bonusFromNumberOfCommands,
            "commandsBonus") + "\n" + getScore(bonusFromTimeTaken, "timeBonus") + "\n" +
            getScore(executionScore + bonusFromNumberOfCommands + bonusFromNumberOfCommands,
                "totalScore"));
  }

  private String getScore(int score, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = score;
    formatter.applyPattern(winMessages.getString(key));
    return formatter.format(currNum);
  }

}
