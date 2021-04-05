package ooga.view.level;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import ooga.view.ScreenCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Board extends GridPane {
  private static final String BOARD_PROPERTIES = "Board";

  private int rows; // TODO: passed from BoardState
  private int cols; // TODO: passed from BoardState
  private ArrayList<Integer> states; // TODO: passed from BoardState
  private double gridXSize;
  private double gridYSize;
  private double xSize;
  private double ySize;

  public Board() {
    ResourceBundle boardValues = ResourceBundle.getBundle(ScreenCreator.RESOURCES + BOARD_PROPERTIES);
    rows = 10; // TODO: remove after wired with model
    cols = 14; // TODO: remove after wired with model
    gridXSize = Double.parseDouble(boardValues.getString("gridXSize"));
    gridYSize = Double.parseDouble(boardValues.getString("gridYSize"));
    states = new ArrayList<>(); // TODO: remove after wired with model
    // TODO: remove after wired with model
    Random random = new Random();
    for (int i = 0; i < rows*cols; i ++) {
      int state = random.nextInt(3);
      states.add(state);
    }

    this.getStyleClass().add("board");
    xSize = gridXSize / cols;
    ySize = gridYSize / rows;
    makeGrid();
  }

  private void makeGrid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Rectangle block = new Rectangle(xSize, ySize);
        block.getStyleClass().add("state-" + states.get(j + i * cols));
        this.add(block, j, i);
      }
    }
  }
}
