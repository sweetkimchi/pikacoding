package ooga.view.animation;

import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.level.Board;
import ooga.view.level.ControlPanel;
import ooga.view.level.LevelView;
import ooga.view.level.codearea.CodeArea;

public class AnimationController {

  private Timeline timeline;
  private boolean codeIsRunning;
  private boolean queueFinished;
  private boolean step;

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

    codeIsRunning = false;
    queueFinished = true;
    step = false;

    initializeAnimationTimeline();
  }

  //TODO: remove after debug
  //I'm using this because for some reason, clicking step doesn't play the initial animation (does nothing)
  private double dummy = 1;

  private void initializeAnimationTimeline() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      /**
       * if queue is finished run the next command
       * if the que is not finished, it means that the turn is not over yet so execute the animation for the turn
       */
      // System.out.println("Animation running");
      if (queueFinished) {
        if (step && dummy != 1) {
          timeline.stop();
          step = false;
        }
        dummy++;
        viewController.runNextCommand();
        queueFinished = false;
      } else {
        updateAnimationForFrontEnd();
      }
      setAnimationSpeed();
    }));
  }

  public void pause() {
//    System.out.println("pause");
    timeline.stop();
  }

  public void play() {
//    System.out.println("play");
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
    dummy = 1;
    codeArea.setLineIndicators(new HashMap<>());

    levelView.resetScore();

//    System.out.println("reset");
  }

  public void step() {
//    System.out.println("step");
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
    if (queueFinished) {
      viewController.runNextCommand();
      queueFinished = false;
    } else {
      updateAnimationForFrontEnd();
    }

  }

  public void declareEndofAnimation() {
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
