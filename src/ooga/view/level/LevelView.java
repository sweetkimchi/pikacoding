package ooga.view.level;

import javafx.scene.layout.BorderPane;
import ooga.controller.FrontEndExternalAPI;

public class LevelView extends BorderPane {

  private FrontEndExternalAPI viewController;
  private MenuBar menuBar;
  private Board board;
  private CodeArea codeArea;
  private ControlPanel controlPanel;

  public LevelView() {
    menuBar = new MenuBar();
    board = new Board();
    codeArea = new CodeArea();
    controlPanel = new ControlPanel();
    this.setTop(menuBar);
    this.setCenter(board);
    this.setRight(codeArea);
    this.setBottom(controlPanel);
  }

}
