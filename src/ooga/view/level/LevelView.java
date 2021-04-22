package ooga.view.level;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ooga.controller.Controller;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.view.ScreenCreator;
import ooga.view.animation.AnimationController;
import ooga.view.level.codearea.CodeArea;

/**
 * Main view class for levels. Contains all the main level view elements (board, code area, etc.)
 *
 * @author David Li
 * @author Ji Yun Hyo
 */
public class LevelView extends BorderPane {

  public static final String LEVEL_PROPERTIES = "Level";

  private final int level;
  private final FrontEndExternalAPI viewController;
  private final ScreenCreator screenCreator;
  private final MenuBar menuBar;
  private Label scoreDisplay;
  private final Board board;
  private final AnimationController animationController;
  private GridPane rightPane;
  private final CodeArea codeArea;
  private final ControlPanel controlPanel;
  private Label description;

  private int startingApples;
  private int score;

  //TODO: remove after debug
  //I'm using this because for some reason, clicking step doesn't play the initial animation (does nothing)
  private double dummy = 1;

  public LevelView(int level, FrontEndExternalAPI viewController, ScreenCreator screenCreator) {
    this.viewController = viewController;
    this.screenCreator = screenCreator;
    this.level = level;
    this.getStylesheets().add(this.screenCreator.getCurrentStyleSheet());
    menuBar = new MenuBar(e -> openPauseMenu());
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();

    initializeViewElements();
    animationController = new AnimationController(this, viewController, codeArea, board, controlPanel);
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    codeArea.setAvailableCommands(availableCommands);
  }

  public void initializeBoard(GameGridData gameGridData, InitialState initialState) {
    board.initializeBoard(gameGridData, initialState);
  }

  private void openPauseMenu() {
    VBox pauseMenu = new VBox();
    pauseMenu.getStyleClass().add("start-screen");
    pauseMenu.getChildren().add(new Label("Paused"));
    Button resumeButton = new Button("Resume");
    resumeButton.setOnAction(e -> {
      this.setCenter(board);
      this.setRight(rightPane);
      this.setBottom(controlPanel);
    });
    pauseMenu.getChildren().add(resumeButton);
    Button startMenuButton = new Button("Home");
    startMenuButton.setOnAction(e -> screenCreator.loadStartMenu());
    pauseMenu.getChildren().add(startMenuButton);

    Button levelSelectorButton = new Button("Level Selector");
    levelSelectorButton.setOnAction(e -> screenCreator.loadLevelSelector());
    pauseMenu.getChildren().add(levelSelectorButton);

    this.setCenter(pauseMenu);
    this.setRight(null);
    this.setBottom(null);
  }

  private void initializeViewElements() {
    ResourceBundle levelResources = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + LEVEL_PROPERTIES);
    menuBar.setMinHeight(Double.parseDouble(levelResources.getString("MenuBarHeight")));
    codeArea.setMinWidth(Double.parseDouble(levelResources.getString("CodeAreaWidth")));
    controlPanel.setMinHeight(Double.parseDouble(levelResources.getString("ControlPanelHeight")));
    controlPanel.setButtonAction("Button1_Reset", e -> animationController.reset());
    controlPanel.setButtonAction("Button2_Play", e -> animationController.play());
    controlPanel.setButtonAction("Button3_Pause", e -> animationController.pause());
    controlPanel.setButtonAction("Button4_Step", e -> animationController.step());
    this.setTop(menuBar);
    scoreDisplay = new Label("Apples Left for Pikachu: ");

    board.getChildren().add(scoreDisplay);
    StackPane.setAlignment(scoreDisplay, Pos.TOP_LEFT);
    this.setCenter(board);
    createRight(levelResources);
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

  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    board.updateAvatarPosition(id, xCoord, yCoord);
  }

  public void declareEndOfAnimation() {
    animationController.declareEndofAnimation();
  }

  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    codeArea.setLineIndicators(lineUpdates);
  }

  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    board.updateBlockPosition(id, xCoord, yCoord);
  }

  public void updateBlock(int id, boolean b) {
    board.updateBlock(id, b);
  }

  public void winLevel() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
    this.setTop(null);
    this.setCenter(new WinScreen(score, e -> screenCreator.loadStartMenu(),
        e -> viewController.initializeLevel(level + 1), level == Controller.NUM_LEVELS));
    this.setRight(null);
    this.setBottom(null);
  }

  public void setScore(int score) {
    scoreDisplay.setText("Apples Left for Pikachu: " + score);
    this.score = score;
  }

  public void setBlockNumber(int id, int newDisplayNum) {
    board.setBlockNumber(id, newDisplayNum);
  }

  public void setDescription(String description) {
    this.description.setText(description);
  }

  public void setStartingApples(int apples) {
    startingApples = apples;
    setScore(startingApples);
  }

  public void loseLevel() {
    animationController.reset();
    this.setCenter(new LoseScreen(e -> {
      this.setTop(menuBar);
      this.setCenter(board);
      this.setRight(rightPane);
      this.setBottom(controlPanel);
    }));
    this.setTop(null);
    this.setRight(null);
    this.setBottom(null);
  }

  public void resetScore() {
    setScore(startingApples);
  }
}
