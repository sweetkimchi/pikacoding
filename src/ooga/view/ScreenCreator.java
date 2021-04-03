package ooga.view;

import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.animation.AnimationPane;
import ooga.view.level.LevelView;

/**
 *
 */
public class ScreenCreator {

  public static final String RESOURCES = ScreenCreator.class.getPackageName() + ".resources.";
  private static final String WINDOW_PROPERTIES = "Window";

  private Timeline timeline;
  FrontEndExternalAPI viewController;
  private Stage stage;
  private AnimationPane animationPane;
  private LevelView levelView;

  /**
   * Default constructor
   */
  public ScreenCreator(FrontEndExternalAPI viewController, Stage stage) {
    this.viewController = viewController;
    this.stage = stage;

    animationPane = new AnimationPane(viewController);
    levelView = new LevelView(this.viewController);

    initializeStage();

    // debug statement
    System.out.println("Screen launched");
  }

  private void initializeStage() {
    ResourceBundle windowResources = ResourceBundle.getBundle(RESOURCES + WINDOW_PROPERTIES);
    stage.setTitle(windowResources.getString("Title"));
    int width = Integer.parseInt(windowResources.getString("Width"));
    int height = Integer.parseInt(windowResources.getString("Height"));
    stage.setMinWidth(width);
    stage.setMinHeight(height);
    stage.setScene(new Scene(levelView, width, height));
    stage.show();
  }

  private void runSimulation() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

      //     updateTurtleStates();
      setAnimationSpeed();
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    timeline.setRate(300);
  }

  private void setAnimationSpeed() {
    //   timeline.setRate(userCommand.getAnimationSpeed());
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    animationPane.updateCommandQueue(commandType, commandValues);
  }


  public void setPosition(double x, double y) {
    animationPane.setPosition(x, y);
  }

  public void setActiveAvatar(int avatarID) {
    animationPane.setActiveAvatar(avatarID);
  }
}