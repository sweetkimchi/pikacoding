package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.grid.gridData.BoardState;
import ooga.view.level.LevelView;

/**
 * Responsible for setting up the JavaFX stage and creating the primary views.
 *
 * @author David Li
 */
public class ScreenCreator {

  public static final String RESOURCES = ScreenCreator.class.getPackageName() + ".resources.";
  private static final String WINDOW_PROPERTIES = "Window";

  FrontEndExternalAPI viewController;
  private Stage stage;
  private LevelView levelView;

  /**
   * Default constructor
   */
  public ScreenCreator(FrontEndExternalAPI viewController, Stage stage) {
    this.viewController = viewController;
    this.stage = stage;

    initializeStage();

    // debug statement
    System.out.println("Screen launched");
  }

  public LevelView getLevelView() {
    return levelView;
  }

  public void loadLevel(int level) {
    levelView = new LevelView(this.viewController);

    ResourceBundle windowResources = ResourceBundle.getBundle(RESOURCES + WINDOW_PROPERTIES);
    int width = Integer.parseInt(windowResources.getString("Width"));
    int height = Integer.parseInt(windowResources.getString("Height"));
    Scene scene = new Scene(levelView, width, height);
    stage.setScene(scene);
  }

  private void initializeStage() {
    ResourceBundle windowResources = ResourceBundle.getBundle(RESOURCES + WINDOW_PROPERTIES);
    stage.setTitle(windowResources.getString("Title"));
    int width = Integer.parseInt(windowResources.getString("Width"));
    int height = Integer.parseInt(windowResources.getString("Height"));
    stage.setMinWidth(width);
    stage.setMinHeight(height);
    stage.show();
  }

  public void setScore(int score) {
    System.out.println("SCORE (ScreenCreator): " + score);
  }
}