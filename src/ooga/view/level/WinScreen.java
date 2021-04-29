package ooga.view.level;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.view.ScreenCreator;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;

/**
 * Screen that is displayed when a player wins a single player level
 * @author David Li
 * @author Kathleen Chen
 */
public class WinScreen extends VBox {

  public static final String SCREEN_MESSAGES = "ScreenStrings";
  private static final int NO_NUM = 0;

  private final ResourceBundle winMessages;
  private final GUIElementInterface GUIFactory;

  /**
   * Main constructor
   * @param score Score that the player achieved
   * @param homeAction Action that takes the player to the home screen
   * @param nextLevelAction Action that starts the next level
   * @param isLastLevel If the level was the last level (i.e. there is no next level)
   */
  public WinScreen(int score, EventHandler<ActionEvent> homeAction,
      EventHandler<ActionEvent> nextLevelAction, boolean isLastLevel) {
    GUIFactory = new GUIElementFactory();
    winMessages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + SCREEN_MESSAGES);
    this.getStyleClass().add("start-screen");

    Label winMessage = GUIFactory.makeLabel(winMessages, "win", "title", NO_NUM);
    this.getChildren().add(winMessage);
    Label scoreMessage = GUIFactory.makeLabel(winMessages, "winScore", "default-string", score);
    this.getChildren().add(scoreMessage);

    if (!isLastLevel) {
      Button nextLevelButton = GUIFactory
          .makeButton(winMessages, nextLevelAction, "next", "default-button", "next", NO_NUM);
      this.getChildren().add(nextLevelButton);
    }

    Button homeButton = GUIFactory
        .makeButton(winMessages, homeAction, "home", "default-button", "home", NO_NUM);
    this.getChildren().add(homeButton);
  }
}
