package ooga.view;

import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.level.LevelView;
import ooga.view.level.MultiplayerLevelView;

/**
 * Responsible for setting up the JavaFX stage and creating the primary views.
 *
 * @author David Li
 */
public class ScreenCreator {

  public static final String RESOURCES = ScreenCreator.class.getPackageName() + ".resources.properties.";
  private static final String WINDOW_PROPERTIES = "Window";
  private static final String IDS_FOR_TESTING = RESOURCES + "IdsForTesting";
  public static final ResourceBundle idsForTests = ResourceBundle.getBundle(IDS_FOR_TESTING);

  private final FrontEndExternalAPI viewController;
  private final Stage stage;
  private LevelView levelView;

  private final double width;
  private final double height;
  private StartMenu startMenu;
  private String lang;

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
  public void initializeMultiLevelView(int level) {
    levelView = new MultiplayerLevelView(level, viewController, this);
    Scene scene = new Scene(levelView, width, height);
    stage.setScene(scene);
  }

  /**
   * Creates a new LevelView and loads it into the stage
   * @param level Level number
   */
  public void initilaizeSingleLevelView(int level) {
    levelView = new LevelView(level, viewController, this);
    Scene scene = new Scene(levelView, width, height);
    stage.setScene(scene);
  }

  /**
   * Opens up the start menu
   */
  public void loadStartMenu() {
    startMenu = new StartMenu(e -> loadMatchIdSelector());
    Scene scene = new Scene(startMenu, width, height);
    stage.setScene(scene);
  }

  public void loadMatchIdSelector() {
    MatchIdSelector matchIdSelector = new MatchIdSelector(this);
    matchIdSelector.getStylesheets().add(startMenu.getStyleSheet());
    Scene scene = new Scene(matchIdSelector, width, height);
    stage.setScene(scene);
  }

  public void setMatchId(int id) { viewController.setMatchId(id); }

  public void loadGameTypeSelector() {
    GameTypeSelector gameTypeSelector = new GameTypeSelector(e -> loadSingleLevelSelector(), e -> loadTeamSelector());
    gameTypeSelector.getStylesheets().add(startMenu.getStyleSheet());
    Scene scene = new Scene(gameTypeSelector, width, height);
    stage.setScene(scene);
  }

  public void loadTeamSelector() {
    TeamSelector teamSelector = new TeamSelector(e -> loadMultiLevelSelector(), this);
    teamSelector.getStylesheets().add(startMenu.getStyleSheet());
    Scene scene = new Scene(teamSelector, width, height);
    stage.setScene(scene);
  }

  public void setTeamNum(int team) { viewController.setTeamNum(team); }

  /**
   * Opens up the level selector
   */
  public void loadSingleLevelSelector() {
    setTeamNum(0);
    LevelSelector levelSelector = new LevelSelector(viewController::initializeSingleLevel);
    levelSelector.getStylesheets().add(startMenu.getStyleSheet());
    Scene scene = new Scene(levelSelector, width, height);
    stage.setScene(scene);
  }

  public void loadMultiLevelSelector() {
    LevelSelector levelSelector = new LevelSelector(viewController::initializeMultiLevel);
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