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
    codeIsRunning = false;
    queueFinished = true;
    step = false;
    initializeAnimationTimeline();
  }

  private void initializeAnimationTimeline() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      if (queueFinished) {
        if (step && isInitialStep != 1) {
          timeline.stop();
          step = false;
        }
        isInitialStep++;
        viewController.runNextCommand();
        queueFinished = false;
      } else {
        updateAnimationForFrontEnd();
      }
      setAnimationSpeed();
    }));
  }

  public void pause() {
    timeline.stop();
  }

  public void play() {
    step = false;
    if (!codeIsRunning) {
      reset();
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
    runSimulation();
  }

  public void reset() {
    codeIsRunning = false;
    board.reset();
    timeline.stop();
    isInitialStep = 1;
    codeArea.setLineIndicators(new HashMap<>());
    levelView.resetScore();
  }

  public void step() {
    if (!codeIsRunning) {
      reset();
      viewController.parseCommands(codeArea.getProgram());
      queueFinished = false;
      codeIsRunning = true;
    }
    runSimulation();
    step = true;
    if (queueFinished) {
      viewController.runNextCommand();
      queueFinished = false;
    } else {
      updateAnimationForFrontEnd();
    }
  }

  public void declareEndOfAnimation() {
    codeIsRunning = false;
    timeline.stop();
  }

  private void runSimulation() {
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

}
