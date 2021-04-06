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

  // TODO: remove after debugging
  Person person1;
  Block block1;

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
      int state = 0;
      if(i < cols || i % cols == 0 || i % cols == cols - 1 || i >= cols * (rows -1)) {
        state = 2;
      }
      else {
        state = random.nextInt(2);
      }
      states.add(state);
    }

    this.getStyleClass().add("board");
    xSize = gridXSize / cols;
    ySize = gridYSize / rows;
    makeGrid();
    makeTestingAvatars();
  }

  // TODO: remove after debugging
  private void makeTestingAvatars() {
    block1 = new Block(5, 7, xSize - 5.0, ySize - 5.0, this, "10");
    person1 = new Person(3, 7, xSize, ySize, this);
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

  public void moveAvatar(double xCoord, double yCoord){
    person1.movePerson(xCoord,yCoord);
  }
}
