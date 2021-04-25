package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.ScreenCreator;
import ooga.view.level.codearea.MultiplayerWinScreen;

public class MultiplayerLevelView extends LevelView {

  public MultiplayerLevelView(int level, FrontEndExternalAPI viewController,
      ScreenCreator screenCreator) {
    super(level, viewController, screenCreator);
  }

  @Override
  public void winLevel() {
    try {
      Thread.sleep(2000);
    } catch (Exception ignored) {

    }
    clearScreen();
    // TODO: pass in scores
    this.setCenter(new MultiplayerWinScreen(0, e -> getScreenCreator().loadStartMenu(),
        e -> {
          getAnimationController().reset();
          restoreScreen();
        }, e -> indicateLevelFinished()));
  }

  @Override
  protected void openPauseMenu() {
    VBox pauseMenu = new VBox();
    pauseMenu.getStyleClass().add("start-screen");
    pauseMenu.getChildren().add(new Label("Paused"));
    Button resumeButton = new Button("Resume");
    resumeButton.setOnAction(e -> {
      restoreScreen();
    });
    pauseMenu.getChildren().add(resumeButton);
    clearScreen();
    this.setCenter(pauseMenu);
  }

  private void indicateLevelFinished() {
    //TODO: tell database that this team is done
  }

}
