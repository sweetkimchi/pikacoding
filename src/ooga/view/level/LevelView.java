package ooga.view.level;

import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.ScreenCreator;
import ooga.view.animation.AnimationPane;
import ooga.view.level.codearea.CodeArea;

/**
 * Main view class for levels.
 * Contains all the main level view elements (board, code area, etc.)
 *
 * @author David Li
 */
public class LevelView extends BorderPane {

  private static final String LEVEL_PROPERTIES = "Level";

  private FrontEndExternalAPI viewController;
  private MenuBar menuBar;
  private Board board;
  private CodeArea codeArea;
  private ControlPanel controlPanel;

  private AnimationPane animationPane;
  private Timeline timeline;

  public LevelView(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    menuBar = new MenuBar();
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    animationPane = new AnimationPane(this.viewController);
    initializeViewElements();
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

  private void initializeViewElements() {
    ResourceBundle levelResources = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + LEVEL_PROPERTIES);
    menuBar.setMinHeight(Double.parseDouble(levelResources.getString("MenuBarHeight")));
    codeArea.setMinWidth(Double.parseDouble(levelResources.getString("CodeAreaWidth")));
    controlPanel.setMinHeight(Double.parseDouble(levelResources.getString("ControlPanelHeight")));
    this.setTop(menuBar);
    this.setCenter(board);
    this.setRight(codeArea);
    this.setBottom(controlPanel);
  }

  private void runProgram() {
    viewController.parseAndExecuteCommands(codeArea.getProgram());
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

}
