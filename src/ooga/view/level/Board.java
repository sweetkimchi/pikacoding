package ooga.view.level;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.AvatarData;
import ooga.view.ScreenCreator;

public class Board extends StackPane {

  private static final String BOARD_PROPERTIES = "Board";

  private GridPane gridLayer;
  private SpriteLayer spriteLayer;

  private int rows;
  private int cols;
  private ArrayList<Integer> states; // TODO: passed from BoardState
  private double gridXSize;
  private double gridYSize;
  private double xSize;
  private double ySize;

//  Avatar avatar1;
//  Block block1;

  public Board() {
  }


  public void reset() {
    spriteLayer.resetAvatarLocations();
    spriteLayer.resetBlockData();
    spriteLayer.resetQueue();
    spriteLayer.resetAnimationQueue();
  }

  public void initializeBoard(GameGridData gameGridData, InitialState initialState) {
    ResourceBundle boardValues = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + BOARD_PROPERTIES);
    rows = gameGridData.getRows();
    cols = gameGridData.getColumns();
    gridXSize = Double.parseDouble(boardValues.getString("gridXSize"));
    gridYSize = Double.parseDouble(boardValues.getString("gridYSize"));
    gridLayer = new GridPane();
    spriteLayer = new SpriteLayer(gridXSize, gridYSize);
    this.getChildren().addAll(gridLayer, spriteLayer);
    states = new ArrayList<>();
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
    spriteLayer.initializeBlocks(initialState.getAllBlockData());
    spriteLayer.initializeAvatars(initialState.getAllAvatarLocations());
  }

  private void makeGrid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Rectangle block = new Rectangle(xSize, ySize);
        block.getStyleClass().add("state-" + states.get(j + i * cols));
        gridLayer.add(block, j, i);
      }
    }
  }

  public void updateAvatarPositions(int id, int xCoord, int yCoord) {
    spriteLayer.updateAvatarPositions(id, xCoord, yCoord);
  }

  //TODO: refactor (CommandExecuter)
  public void updateFrontEndElements(Map<String, AvatarData> updates) {
    for (Map.Entry<String, AvatarData> entry : updates.entrySet()) {
      System.out.println("Updating: " + entry.getKey());
      for (Integer value : entry.getValue().getPositionUpdates()) {
        System.out.println("Values: " + value);
      }

    }
  }

  public int getNumberOfAvatars() {
    return spriteLayer.getNumberOfAvatars();
  }

  public boolean updateAnimationForFrontEnd() {
    return spriteLayer.updateAnimationForFrontEnd();
  }
}
