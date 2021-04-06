package ooga.view.level;

import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.AvailableCommands;
import ooga.view.ScreenCreator;
import ooga.model.animation.AnimationPane;
import ooga.view.level.codearea.CodeArea;

/**
 * Main view class for levels.
 * Contains all the main level view elements (board, code area, etc.)
 *
 * @author David Li
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

  private AnimationPane animationPane;
  private Timeline timeline;

  private boolean codeIsRunning;

  public LevelView(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    this.getStylesheets().add(DEFAULT_CSS);
    menuBar = new MenuBar();
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    animationPane = new AnimationPane(this.viewController);
    codeIsRunning = false;
    initializeViewElements();



  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    animationPane.updateCommandQueue(commandType, commandValues);
  }


  public void setPosition(double x, double y, int id) {
    animationPane.setPosition(x, y);
  }

  public void setActiveAvatar(int avatarID) {
    animationPane.setActiveAvatar(avatarID);
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    codeArea.setAvailableCommands(availableCommands);
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
    runSimulation();
  }

  private void reset() {
    codeIsRunning = false;
    System.out.println("reset");
  }

  private void step() {
    System.out.println("step");
    if (!codeIsRunning) {
      viewController.parseCommands(codeArea.getProgram());
      codeIsRunning = true;
    }
    viewController.runNextCommand();
  }

  private void runSimulation() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

      //     updateTurtleStates();
      viewController.runNextCommand();
      setAnimationSpeed();
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    timeline.setRate(300);
  }

  private void setAnimationSpeed() {
    // TODO: remove after debugging
    timeline.setRate(2);
  }

}
