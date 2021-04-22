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

  public static final String RESOURCES = ScreenCreator.class.getPackageName() + ".resources.properties.";
  private static final String WINDOW_PROPERTIES = "Window";

  private final FrontEndExternalAPI viewController;
  private final Stage stage;
  private LevelView levelView;

  private final double width;
  private final double height;
  private StartMenu startMenu;

  /**
   * Default constructor
   */
  public ScreenCreator(FrontEndExternalAPI viewController, Stage stage) {
    this.viewController = viewController;
    this.stage = stage;
    ResourceBundle windowResources = ResourceBundle.getBundle(RESOURCES + WINDOW_PROPERTIES);
    width = Double.parseDouble(windowResources.getString("Width"));
    height = Double.parseDouble(windowResources.getString("Height"));
    initializeStage();
  }

  public LevelView getLevelView() {
    return levelView;
  }

  public String getCurrentStyleSheet() {
    return startMenu.getStyleSheet();
  }

  /**
   * Creates a new LevelView and loads it into the stage
   * @param level Level number
   */
  public void initializeLevelView(int level) {
    levelView = new LevelView(level, viewController, this);
    Scene scene = new Scene(levelView, width, height);
    stage.setScene(scene);
  }

  /**
   * Opens up the start menu
   */
  public void loadStartMenu() {
    startMenu = new StartMenu(e -> loadLevelSelector());
    Scene scene = new Scene(startMenu, width, height);
    stage.setScene(scene);
  }

  /**
   * Opens up the level selector
   */
  public void loadLevelSelector() {
    LevelSelector levelSelector = new LevelSelector(viewController::initializeLevel);
    levelSelector.getStylesheets().add(startMenu.getStyleSheet());
    Scene scene = new Scene(levelSelector, width, height);
    stage.setScene(scene);
  }

  private void initializeStage() {
    ResourceBundle windowResources = ResourceBundle.getBundle(RESOURCES +  WINDOW_PROPERTIES);
    stage.setTitle(windowResources.getString("Title"));
    stage.setMinWidth(width);
    stage.setMinHeight(height);
    stage.show();
  }

}