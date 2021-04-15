package ooga.view.level;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.BoardState;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.AvatarData;
import ooga.view.ScreenCreator;
import ooga.view.StartMenu;
import ooga.view.level.codearea.CodeArea;

/**
 * Main view class for levels.
 * Contains all the main level view elements (board, code area, etc.)
 *
 * @author David Li
 * @author Ji Yun Hyo
 */
public class LevelView extends BorderPane {

  public static final String LEVEL_PROPERTIES = "Level";

  private final FrontEndExternalAPI viewController;
  private final ScreenCreator screenCreator;
  private final MenuBar menuBar;
  private final Board board;
  private final CodeArea codeArea;
  private final ControlPanel controlPanel;

  private int score;

  private Timeline timeline;

  private boolean codeIsRunning;
  private boolean queueFinished;
  private boolean step;

  //TODO: remove after debug
  //I'm using this because for some reason, clicking step doesn't play the initial animation (does nothing)
  private double dummy = 1;

  public LevelView(FrontEndExternalAPI viewController, ScreenCreator screenCreator) {
    this.viewController = viewController;
    this.screenCreator = screenCreator;
    this.getStylesheets().add(StartMenu.DEFAULT_CSS);
    menuBar = new MenuBar(e -> openPauseMenu());
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    codeIsRunning = false;
    queueFinished = true;
    step = false;


    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

      /**
       * if queue is finished run the next command
       * if the que is not finished, it means that the turn is not over yet so execute the animation for the turn
       */
     // System.out.println("Animation running");
      if(queueFinished){
        if(step && dummy != 1){
          timeline.stop();
          step = false;
        }
        dummy++;
        viewController.runNextCommand();
        queueFinished = false;

      }else{
        updateAnimationForFrontEnd();
      }
      setAnimationSpeed();

    }));
    initializeViewElements();
  }


  public void setPosition(double x, double y, int id) {

  }

  public void setActiveAvatar(int avatarID) {

  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    codeArea.setAvailableCommands(availableCommands);
  }

  public void initializeBoard(GameGridData gameGridData, InitialState initialState) {
    board.initializeBoard(gameGridData, initialState);
  }

  private void openPauseMenu() {
    VBox pauseMenu = new VBox();
    pauseMenu.getChildren().add(new Label("Paused"));
    Button resumeButton = new Button("Resume");
    resumeButton.setOnAction(e -> {
      this.setCenter(board);
      this.setRight(codeArea);
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

  private void updateAnimationForFrontEnd() {
    queueFinished = board.updateAnimationForFrontEnd();
  }

  private void initializeViewElements() {
    ResourceBundle levelResources = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + LEVEL_PROPERTIES);
    menuBar.setMinHeight(Double.parseDouble(levelResources.getString("MenuBarHeight")));
    codeArea.setMinWidth(Double.parseDouble(levelResources.getString("CodeAreaWidth")));
    controlPanel.setMinHeight(Double.parseDouble(levelResources.getString("ControlPanelHeight")));
    controlPanel.setButtonAction("Button1_Reset", e -> reset());
    controlPanel.setButtonAction("Button2_Play", e -> play());
    controlPanel.setButtonAction("Button3_Pause", e -> pause());
    controlPanel.setButtonAction("Button4_Step", e -> step());
    this.setTop(menuBar);
    this.setCenter(board);
    this.setRight(codeArea);
    this.setBottom(controlPanel);
  }

  private void pause() {
    viewController.winLevel();
//    System.out.println("pause");
//    timeline.stop();
  }

  private void play() {
    System.out.println("play");
    step = false;
    if (!codeIsRunning) {
      reset();
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
    runSimulation();


  }

  private void reset() {
    codeIsRunning = false;
    board.reset();
    timeline.stop();
    dummy = 1;
    codeArea.setLineIndicators(new HashMap<>());

    System.out.println("reset");
  }

  private void step() {

    System.out.println("step");
    if (!codeIsRunning) {
      reset();
      viewController.parseCommands(codeArea.getProgram());
      queueFinished = false;
      codeIsRunning = true;
    }


    runSimulation();

    step = true;
    /**
     * if queue is finished run the next command
     * if the que is not finished, it means that the turn is not over yet so execute the animation for the turn
     */
    if(queueFinished){
      viewController.runNextCommand();
      queueFinished = false;
    }else{
      updateAnimationForFrontEnd();
    }

  }

  private void runSimulation() {
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    timeline.setRate(100);
  }

  private void setAnimationSpeed() {
    // TODO: remove after debugging
    timeline.setRate(controlPanel.getSliderSpeed());
  }

  public void updateAvatarPositions(int id, int xCoord, int yCoord) {
    board.updateAvatarPositions(id, xCoord, yCoord);
  }

  public void updateFrontEndElements(Map<String, AvatarData> updates) {
    board.updateFrontEndElements(updates);
  }

  public void declareEndOfAnimation() {
    codeIsRunning = false;
    timeline.stop();
  }

  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    codeArea.setLineIndicators(lineUpdates);
  }

  public void updateBlockPositions(int id, int xCoord, int yCoord) {
    board.updateBlockPositions(id,xCoord,yCoord);
  }

  public void updateBlock(int id, boolean b) {
    board.updateBlock(id,b);
  }

  public void winLevel() {
    declareEndOfAnimation();
    this.setCenter(new WinScreen(score));
    this.setRight(null);
    this.setBottom(null);
  }

  public void setScore(int score) {
    this.score = score;
  }
}
