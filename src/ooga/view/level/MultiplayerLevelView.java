package ooga.view.level;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.view.ScreenCreator;

/**
 * Level view for a multiplayer game
 *
 * @author David Li
 */
public class MultiplayerLevelView extends LevelView {

  private Label timerDisplay;

  /**
   * Main constructor
   * @param level Level number
   * @param viewController View controller
   * @param screenCreator Screen creator
   */
  public MultiplayerLevelView(int level, FrontEndExternalAPI viewController,
      ScreenCreator screenCreator) {
    super(level, viewController, screenCreator);
  }

  /**
   * Sets the teammate's available commands
   * @param availableCommands Available commands
   */
  public void setAvailableCommandsOtherPlayer(AvailableCommands availableCommands) {
    getCodeArea().setAvailableCommandsOtherPlayer(availableCommands);
  }

  /**
   * Displays the win screen for a multiplayer game
   * @param executionScore Score from number of lines executed
   * @param bonusFromNumberOfCommands Bonus from number of commands used
   * @param bonusFromTimeTaken Bonus from amount of time used
   */
  @Override
  public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    try {
      Thread.sleep(2000);
    } catch (Exception ignored) {

    }
    clearScreen();
    // TODO: pass in scores
    MultiplayerWinScreen multiplayerWinScreen = new MultiplayerWinScreen(
        e -> getScreenCreator().loadStartMenu(),
        e -> {
          getAnimationController().reset();
          restoreScreen();
        }, e -> indicateLevelFinished());
    multiplayerWinScreen.setScores(executionScore, bonusFromNumberOfCommands, bonusFromTimeTaken);
    this.setCenter(multiplayerWinScreen);
  }

  /**
   * Changes the displayed time
   * @param timeLeft Amount of time left
   */
  @Override
  public void updateTime(int timeLeft) {
    String minutes = "" + timeLeft / 60;
    String seconds = "" + timeLeft % 60;
    if (timeLeft % 60 < 10) {
      seconds = "0" + timeLeft % 60;
    }
    timerDisplay.setText(minutes + ":" + seconds);
  }

  @Override
  protected void initializeViewElements() {
    super.initializeViewElements();
    timerDisplay = new Label();

    getBoard().getChildren().add(timerDisplay);
    StackPane.setAlignment(timerDisplay, Pos.TOP_RIGHT);
  }

  @Override
  protected void openPauseMenu() {
    VBox pauseMenu = makePauseMenu();
    clearScreen();
    this.setCenter(pauseMenu);
  }

  private void indicateLevelFinished() {
    //TODO: tell database that this team is done
    clearScreen();
    Label waitingLabel = new Label("Waiting for other players to finish...");
    this.setCenter(waitingLabel);
  }

}
