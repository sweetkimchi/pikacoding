package ooga.view.level;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.view.ScreenCreator;
import ooga.view.animation.AnimationAPI;
import ooga.view.animation.AnimationController;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;
import ooga.view.level.board.Board;
import ooga.view.level.codearea.CodeArea;
import ooga.view.level.codearea.CommandBlock;
import ooga.view.level.codearea.ProgramListener;

/**
 * Main view class for single-player levels. Contains all the main level view
 * elements (board, code area, etc.)
 *
 * @author David Li
 * @author Ji Yun Hyo
 * @author Kathleen Chen
 */
public class LevelView extends BorderPane implements ProgramListener {

  public static final String LEVEL_PROPERTIES = "Level";
  private static final int NO_NUM = 0;

  private final int level;
  private final FrontEndExternalAPI viewController;
  private final ScreenCreator screenCreator;
  private final MenuBar menuBar;
  private Label scoreDisplay;
  private final Board board;
  private final AnimationAPI animationController;
  private GridPane rightPane;
  private final CodeArea codeArea;
  private final ControlPanel controlPanel;
  private Label description;
  private final ResourceBundle levelResources;
  private final GUIElementInterface GUIFactory;

  private int startingApples;
  private int score;

  /**
   * Main constructor
   * @param level Level number
   * @param viewController The view controller for the application
   * @param screenCreator The screen creator for the application
   */
  public LevelView(int level, FrontEndExternalAPI viewController, ScreenCreator screenCreator) {
    GUIFactory = new GUIElementFactory();
    this.viewController = viewController;
    this.screenCreator = screenCreator;
    this.level = level;
    levelResources = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + LEVEL_PROPERTIES);
    this.getStylesheets().add(this.screenCreator.getCurrentStyleSheet());
    menuBar = new MenuBar(e -> openPauseMenu());
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    initializeViewElements();
    animationController = new AnimationController(this, viewController, codeArea, board,
        controlPanel);
  }

  /**
   * Lets the code area know what commands are available
   * @param availableCommands Available commands
   */
  public void setAvailableCommands(AvailableCommands availableCommands) {
    codeArea.setAvailableCommands(availableCommands);
  }

  /**
   * Sets up the board with initial board data
   * @param gameGridData Data for grid tiles
   * @param initialState Data for avatars and blocks
   */
  public void initializeBoard(GameGridData gameGridData, InitialState initialState) {
    board.initializeBoard(gameGridData, initialState);
  }

  /**
   * Notifies the view controller when the program is updated locally
   */
  @Override
  public void onProgramUpdate() {
    viewController.sendProgramUpdates(codeArea.getProgram());
  }

  /**
   * Updates the local program to sync with the database program
   * @param program New program stack
   */
  public void receiveProgramUpdates(List<CommandBlock> program) {
    codeArea.receiveProgramUpdates(program);
  }

  protected void openPauseMenu() {
    VBox pauseMenu = makePauseMenu();
    Button startMenuButton = GUIFactory
        .makeButton(levelResources, e -> screenCreator.loadStartMenu(), "startMenuButton",
            "default-button", "startMenuButton", NO_NUM);
    pauseMenu.getChildren().add(startMenuButton);

    Button levelSelectorButton = GUIFactory
        .makeButton(levelResources, e -> screenCreator.loadSingleLevelSelector(),
            "levelSelectorButton", "default-button", "levelSelectorButton", NO_NUM);
    pauseMenu.getChildren().add(levelSelectorButton);
    animationController.pause();

    clearScreen();
    this.setCenter(pauseMenu);
  }

  protected VBox makePauseMenu() {
    VBox pauseMenu = new VBox();
    pauseMenu.getStyleClass().add("start-screen");
    pauseMenu.getChildren().add(new Label("Paused"));
    Button resumeButton = GUIFactory
        .makeButton(levelResources, e -> restoreScreen(), "resumeButton", "default-button",
            "resumeButton", NO_NUM);
    pauseMenu.getChildren().add(resumeButton);
    return pauseMenu;
  }

  protected CodeArea getCodeArea() {
    return codeArea;
  }

  protected ScreenCreator getScreenCreator() {
    return screenCreator;
  }

  protected AnimationAPI getAnimationController() {
    return animationController;
  }

  protected Board getBoard() {
    return board;
  }

  protected void initializeViewElements() {
    menuBar.setMinHeight(Double.parseDouble(levelResources.getString("MenuBarHeight")));
    createRight(levelResources);
    rightPane.setMinWidth(Double.parseDouble(levelResources.getString("CodeAreaWidth")));
    rightPane.setMaxWidth(Double.parseDouble(levelResources.getString("CodeAreaWidth")));
    codeArea.addProgramListener(this);
    controlPanel.setMinHeight(Double.parseDouble(levelResources.getString("ControlPanelHeight")));
    controlPanel.setButtonAction("Button1_Reset", e -> animationController.reset());
    controlPanel.setButtonAction("Button2_Play", e -> animationController.play());
    controlPanel.setButtonAction("Button3_Pause", e -> animationController.pause());
    controlPanel.setButtonAction("Button4_Step", e -> animationController.step());
    this.setTop(menuBar);
    scoreDisplay = GUIFactory.makeLabel(levelResources, "scoreDisplay", "default-string", NO_NUM);

    board.getChildren().add(scoreDisplay);
    StackPane.setAlignment(scoreDisplay, Pos.TOP_LEFT);
    this.setCenter(board);
    this.setRight(rightPane);
    this.setBottom(controlPanel);
  }

  private void createRight(ResourceBundle levelResources) {
    rightPane = new GridPane();
    VBox descriptionBox = new VBox();
    rightPane.setVgap(8);
    descriptionBox.getStyleClass().add("description-box");
    Label header = new Label("Level " + level);
    header.getStyleClass().add("title");
    description = new Label();
    descriptionBox.getChildren().addAll(header, description);
    rightPane.add(descriptionBox, 0, 0);
    rightPane.add(codeArea, 0, 1);

    RowConstraints rowConstraints = new RowConstraints();
    rowConstraints.setPrefHeight(Double.parseDouble(levelResources.getString("DescriptionHeight")));
    rowConstraints.setMinHeight(Double.parseDouble(levelResources.getString("DescriptionHeight")));
    rightPane.getRowConstraints().add(rowConstraints);
    rightPane.setPadding(new Insets(8, 8, 8, 8));
  }

  /**
   * Update the position of a single avatar
   * @param id Id of the avatar
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    board.updateAvatarPosition(id, xCoord, yCoord);
  }

  /**
   * TODO: Jiyun help
   */
  public void declareEndOfRun() {
    animationController.declareEndOfRun();
  }

  /**
   * Sets which line each avatar is running
   * @param lineUpdates Map from avatar ids to line numbers
   */
  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    codeArea.setLineIndicators(lineUpdates);
  }

  /**
   * Update the position of a single block
   * @param id Id of the block
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    board.updateBlockPosition(id, xCoord, yCoord);
  }

  /**
   * Updates the held state of a block
   * @param id Id of the block
   * @param isHeld Whether the block is held or not
   */
  public void updateBlock(int id, boolean isHeld) {
    board.updateBlock(id, isHeld);
  }

  /**
   * Clears the screen and displays the win screen
   * @param executionScore Score from number of lines executed
   * @param bonusFromNumberOfCommands Bonus from number of commands used
   * @param bonusFromTimeTaken Bonus from amount of time used
   */
  public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    clearScreen();
    this.setCenter(new WinScreen(score, e -> screenCreator.loadStartMenu(),
        e -> viewController.initializeSingleLevel(level + 1),
        level == Integer.parseInt(levelResources.getString("maxLevel"))));
  }

  /**
   * Sets the player score
   * @param score New score
   */
  public void setScore(int score) {
    scoreDisplay.setText("Apples Left for Pikachu: " + score);
    this.score = score;
  }

  /**
   * Sets the number displayed on a block
   * @param id Id of the block
   * @param newDisplayNum New number to be displayed
   */
  public void setBlockNumber(int id, int newDisplayNum) {
    board.setBlockNumber(id, newDisplayNum);
  }

  /**
   * Sets the level description
   * @param description Level description
   */
  public void setDescription(String description) {
    this.description.setText(description);
    this.description.setWrapText(true);
  }

  /**
   * Sets the number of apples the player has by default
   * @param apples Number of apples
   */
  public void setStartingApples(int apples) {
    startingApples = apples;
    setScore(startingApples);
  }

  /**
   * Clears the screen and displays the lose screen
   */
  public void loseLevel() {
    animationController.reset();
    clearScreen();
    this.setCenter(new LoseScreen(e -> {
      restoreScreen();
    }));
  }

  /**
   * Sets the score to its default value
   */
  public void resetScore() {
    setScore(startingApples);
  }

  /**
   * Called when the team has run out of time
   * (Not yet implemented)
   */

  public void timedOut() {
    System.out.println("TIMED OUT!! STOPPING ANIMATION!");
    animationController.stopAnimation();
  }

  /**
   * Animation calls a method in ViewController which in turn checks with the backend and returns
   * time left
   * @param timeLeft Amount of time left
   * (Not yet implemented)
   */
  public void updateTime(int timeLeft) {
  }

  protected void clearScreen() {
    this.setTop(null);
    this.setCenter(null);
    this.setRight(null);
    this.setBottom(null);
  }

  protected void restoreScreen() {
    this.setTop(menuBar);
    this.setCenter(board);
    this.setRight(rightPane);
    this.setBottom(controlPanel);
  }

  /**
   * Resets the animation and board positions
   */
  public void resetAnimation() {
    board.resetAnimation();
    animationController.declareEndOfRun();
  }

  /**
   * Displays a screen letting the player know that the team has finished
   * @param score Score the team achieved
   */
  public void notifyCurrentTeamFinished(int score) {
    Platform.runLater(() -> {
      clearScreen();
      this.setCenter(new TeamFinishedScreen(score));
    });

  }

  /**
   * Displays a screen letting the player know that both teams have finished
   * @param currentScore Score the current team achieved
   * @param otherScore Score the other team achieved
   */
  public void notifyBothTeamsFinished(int currentScore, int otherScore) {
    Platform.runLater(() -> {
      clearScreen();
      this.setCenter(
          new BothTeamsFinishedScreen(currentScore, otherScore,
              e -> screenCreator.loadStartMenu()));
    });

  }
}
