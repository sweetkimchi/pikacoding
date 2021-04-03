package ooga.view.level;

import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.ScreenCreator;

public class LevelView extends BorderPane {

  private static final String LEVEL_PROPERTIES = "Level";

  private FrontEndExternalAPI viewController;
  private MenuBar menuBar;
  private Board board;
  private CodeArea codeArea;
  private ControlPanel controlPanel;

  public LevelView(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    menuBar = new MenuBar();
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    initializeViewElements();
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

}
