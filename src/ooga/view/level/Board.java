package ooga.view.level;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.BoardState;
import ooga.view.ScreenCreator;

public class Board extends GridPane {

  private static final String BOARD_PROPERTIES = "Board";

  private int rows; // TODO: passed from BoardState
  private int cols; // TODO: passed from BoardState
  private ArrayList<Integer> states; // TODO: passed from BoardState
  private double gridXSize;
  private double gridYSize;
  private double xSize;
  private double ySize;

  private Map<String, List<Integer>> initialAvatarLocations;
  private Map<String, BlockData> initialBlockData;

  private Map<Integer, Avatar> avatars;
  private Map<Integer, Block> blocks;
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
    resetAvatarLocations();
    resetBlockData();
  }

  public void initializeBoard(BoardState initialState) {
    ResourceBundle boardValues = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + BOARD_PROPERTIES);
    rows = 10; // TODO: remove after wired with model
    cols = 14; // TODO: remove after wired with model
    gridXSize = Double.parseDouble(boardValues.getString("gridXSize"));
    gridYSize = Double.parseDouble(boardValues.getString("gridYSize"));
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
    this.getStyleClass().add("board");
    xSize = gridXSize / cols;
    ySize = gridYSize / rows;
    makeGrid();
    initializeAvatars(initialState.getAllAvatarLocations());
    initializeBlocks(initialState.getAllBlockData());
//    makeTestingAvatars();
//    Button test = new Button("test");
//    test.setOnAction(event -> testing());
//    this.getChildren().add(test);
  }

//  private void testing() {
//    moveAvatar(50, 10);
//  }

  private void resetAvatarLocations() {
    initialAvatarLocations.forEach((id, location) -> {
      avatars.get(Integer.parseInt(id)).reset();
    });
  }

  private void resetBlockData() {
    initialBlockData.forEach((id, blockData) -> {
      blocks.get(Integer.parseInt(id)).reset();
    });
  }

  private void initializeAvatars(Map<String, List<Integer>> allAvatarLocations) {
    avatars = new HashMap<>();
    initialAvatarLocations = allAvatarLocations;
    initialAvatarLocations.forEach((id, location) -> {
      Avatar avatar = new Avatar(location.get(0), location.get(1), xSize, ySize, this);
      avatars.put(Integer.parseInt(id), avatar);
    });
  }

  private void initializeBlocks(Map<String, BlockData> allBlockData) {
    blocks = new HashMap<>();
    initialBlockData = allBlockData;
    initialBlockData.forEach((id, blockData) -> {
      Block block = new Block(blockData.getLocation().get(0), blockData.getLocation().get(1),
          xSize - 5.0,
          ySize - 5.0, this, "" + blockData.getBlockNumber());
      blocks.put(Integer.parseInt(id), block);
    });
  }

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
        this.add(block, j, i);
      }
    }
  }

}
