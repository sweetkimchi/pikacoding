package ooga.view.level;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Board extends StackPane {
  private int rows; // TODO: passed from BoardState
  private int cols; // TODO: passed from BoardState
  private ArrayList<Integer> states; // TODO: passed from BoardState
  private double gridXSize = 400.0; // TODO: put in resource file
  private double gridYSize = 400.0; // TODO: put in resource file
  private ArrayList<Node> grid;

  public Board() {
    this.getChildren().add(new Label("Board"));
    rows = 10;
    cols = 10;
    states = new ArrayList<>();
    grid = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < rows*cols; i ++) {
      int state = random.nextInt(3);
      states.add(state);
    }
    makeGrid();
  }

  private void makeGrid() {
    Group center = new Group();
    double xSize = gridXSize / cols;
    double ySize = gridYSize / rows;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Rectangle block = new Rectangle( j * xSize, i * ySize, xSize, ySize);
        grid.add(block);
        if (states.get(j + i * cols) == 0) {
          block.setFill(Color.GRAY);
          block.setStroke(Color.BLACK);
        }
        else if (states.get(j + i * cols) == 1) {
          block.setFill(Color.DARKSLATEGRAY);
          block.setStroke(Color.BLACK);
        }
        else {
          block.setFill(Color.WHITE);
          block.setStroke(Color.BLACK);
        }
      }
    }
    center.getChildren().addAll(grid);
    this.getChildren().add(center);
  }
}
