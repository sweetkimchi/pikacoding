package ooga.view.level;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.BoardState;
import ooga.view.ScreenCreator;

public class Board extends StackPane {

  private static final String BOARD_PROPERTIES = "Board";

  private GridPane gridLayer;
  private SpriteLayer spriteLayer;

  private int rows; // TODO: passed from BoardState
  private int cols; // TODO: passed from BoardState
  private ArrayList<Integer> states; // TODO: passed from BoardState
  private double gridXSize;
  private double gridYSize;
  private double xSize;
  private double ySize;

//  Avatar avatar1;
//  Block block1;

  public Board() {
  }

  public void moveAvatar(double xDist, double yDist) {
    avatars.get(1).moveAvatar(xDist, yDist);
    System.out.println();
    //block1.pickUp(xDist, yDist);
  }

  public void reset() {
    spriteLayer.resetAvatarLocations();
    spriteLayer.resetBlockData();
  }

  public void initializeBoard(BoardState initialState) {
    ResourceBundle boardValues = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + BOARD_PROPERTIES);
    rows = 10; // TODO: remove after wired with model
    cols = 14; // TODO: remove after wired with model
    gridXSize = Double.parseDouble(boardValues.getString("gridXSize"));
    gridYSize = Double.parseDouble(boardValues.getString("gridYSize"));
    gridLayer = new GridPane();
    spriteLayer = new SpriteLayer(gridXSize, gridYSize);
    this.getChildren().addAll(gridLayer, spriteLayer);
    states = new ArrayList<>(); // TODO: remove after wired with model
    // TODO: remove after wired with model
    Random random = new Random();
    for (int i = 0; i < rows * cols; i++) {
      int state = 0;
      if (i < cols || i % cols == 0 || i % cols == cols - 1 || i >= cols * (rows - 1)) {
        state = 2;
      } else {
        state = random.nextInt(2);
      }
      states.add(state);
    }
    gridLayer.getStyleClass().add("board");
    xSize = gridXSize / cols;
    ySize = gridYSize / rows;
    makeGrid();
    spriteLayer.setSizes(xSize, ySize);
    spriteLayer.initializeAvatars(initialState.getAllAvatarLocations());
    spriteLayer.initializeBlocks(initialState.getAllBlockData());
//    makeTestingAvatars();
//    Button test = new Button("test");
//    test.setOnAction(event -> testing());
//    this.getChildren().add(test);
  }

//  private void testing() {
//    moveAvatar(50, 10);
//  }

  // TODO: remove after debugging
//  private void makeTestingAvatars() {
//    block1 = new Block(5, 7, xSize - 5.0, ySize - 5.0, this, "10");
//    avatar1 = new Avatar(3, 7, xSize, ySize, this);
//  }

  private void makeGrid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Rectangle block = new Rectangle(xSize, ySize);
        block.getStyleClass().add("state-" + states.get(j + i * cols));
        gridLayer.add(block, j, i);
      }
    }
  }

}
