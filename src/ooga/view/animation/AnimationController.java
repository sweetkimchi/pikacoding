package ooga.view.animation;

import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.level.board.Board;
import ooga.view.level.ControlPanel;
import ooga.view.level.LevelView;
import ooga.view.level.codearea.CodeArea;

/**
 * @author Ji Yun Hyo
 */
public class AnimationController {

  private Timeline timeline;
  private boolean codeIsRunning;
  private boolean queueFinished;
  private boolean step;
  private double isInitialStep;
  private boolean isPaused;

  private final LevelView levelView;
  private final FrontEndExternalAPI viewController;
  private final CodeArea codeArea;
  private final Board board;
  private final ControlPanel controlPanel;

  public AnimationController(LevelView levelView, FrontEndExternalAPI viewController,
      CodeArea codeArea, Board board, ControlPanel controlPanel) {
    this.levelView = levelView;
    this.viewController = viewController;
    this.codeArea = codeArea;
    this.board = board;
    this.controlPanel = controlPanel;
    this.isInitialStep = 1;
    isPaused = true;
    codeIsRunning = false;
    queueFinished = true;
    step = false;
    initializeAnimationTimeline();
    runSimulation();
  }

  private void initializeAnimationTimeline() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      if(!isPaused){
        if (queueFinished) {
          if (step && isInitialStep != 1) {
            isPaused = true;
            step = false;
          }
          isInitialStep++;
          viewController.runNextCommand();
          queueFinished = false;
        } else {
          updateAnimationForFrontEnd();
        }
        setAnimationSpeed();
      }
      viewController.checkTimeLeftOrNot();
    }));
  }

  public void pause() {
    isPaused = true;
  }

  public void play() {
    timeline.play();
    step = false;
    if (!codeIsRunning) {
      reset();
      isPaused = false;
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
  }

  public void reset() {
    codeIsRunning = false;
    board.reset();
    isPaused = true;
    isInitialStep = 1;
    codeArea.setLineIndicators(new HashMap<>());
    levelView.resetScore();
  }

  public void step() {
    isPaused = false;
    if (!codeIsRunning) {
      reset();
      viewController.parseCommands(codeArea.getProgram());
      queueFinished = false;
      codeIsRunning = true;
    }
    step = true;
    if (queueFinished) {
      viewController.runNextCommand();
      queueFinished = false;
    } else {
      updateAnimationForFrontEnd();
    }
  }

  public void declareEndOfRun() {
    codeIsRunning = false;
    isPaused = true;
  }

  private void runSimulation() {
    isPaused = false;
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    timeline.setRate(100);
  }

  private void setAnimationSpeed() {
    timeline.setRate(controlPanel.getSliderSpeed());
  }

  private void updateAnimationForFrontEnd() {
    queueFinished = board.updateAnimationForFrontEnd();
  }

  public void stopAnimation() {
    timeline.stop();
  }
}
