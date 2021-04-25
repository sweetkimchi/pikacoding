package ooga.view.level.codearea;

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

  public MultiplayerWinScreen(int score, EventHandler<ActionEvent> homeAction,
      EventHandler<ActionEvent> goBackAction, EventHandler<ActionEvent> finishedAction) {
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");
    Label winMessage = new Label(winMessages.getString("win"));
    winMessage.getStyleClass().add("title");
    this.getChildren().add(winMessage);

    Label scoreMessage = new Label(getScore(score, "winScore"));
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

  private String getScore(int score, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = score;
    formatter.applyPattern(winMessages.getString(key));
    return formatter.format(currNum);
  }

}
