package ooga.view.level;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.BoardState;
import ooga.model.player.AvatarData;
import ooga.view.ScreenCreator;
import ooga.view.level.codearea.CodeArea;

/**
 * Main view class for levels.
 * Contains all the main level view elements (board, code area, etc.)
 *
 * @author David Li
 * @author Ji Yun Hyo
 */
public class LevelView extends BorderPane {

  private static final String LEVEL_PROPERTIES = "Level";
  private static final String DEFAULT_CSS = ScreenCreator.RESOURCES.replace(".", "/")
          + "default.css";

  private final FrontEndExternalAPI viewController;
  private final MenuBar menuBar;
  private final Board board;
  private final CodeArea codeArea;
  private final ControlPanel controlPanel;
  private boolean queueFinished;

  private Timeline timeline;

  private boolean codeIsRunning;

  //TODO: remove after debug
  private double dummy = 1;

  public LevelView(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    this.getStylesheets().add(DEFAULT_CSS);
    menuBar = new MenuBar();
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    codeIsRunning = false;
    queueFinished = true;

    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

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
      setAnimationSpeed();

    }));
    initializeViewElements();
  }

  private void updateAnimationForFrontEnd() {
    int size = board.getNumberOfAvatars();
    queueFinished = board.updateAnimationForFrontEnd();
  }


  public void setPosition(double x, double y, int id) {

  }

  public void setActiveAvatar(int avatarID) {

  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    codeArea.setAvailableCommands(availableCommands);
  }

  public void initializeBoard(BoardState initialState) {
    board.initializeBoard(initialState);
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
    System.out.println("pause");
    timeline.stop();
  }

  private void play() {
    System.out.println("play");
    if (!codeIsRunning) {
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
    runSimulation();

  }

  private void reset() {
    codeIsRunning = false;
    board.reset();
    System.out.println("reset");
  }

  private void step() {
    System.out.println("step");
    if (!codeIsRunning) {
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
    timeline.stop();

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
    timeline.setRate(300);
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
}
