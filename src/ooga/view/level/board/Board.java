package ooga.view.level.board;


import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.view.ScreenCreator;

public class Board extends StackPane {

  private static final String BOARD_PROPERTIES = "Board";

  private GridPane gridLayer;
  private SpriteLayer spriteLayer;

  private int rows;
  private int cols;
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
    spriteLayer.resetAnimationQueue();
    spriteLayer.resetAvatarImages();
  }

  public void initializeBoard(GameGridData gameGridData, InitialState initialState) {
    setSizing(gameGridData);
    spriteLayer = new SpriteLayer(gridXSize, gridYSize);
    this.getChildren().addAll(gridLayer, spriteLayer);
    gridLayer.getStyleClass().add("board");
    makeGrid(gameGridData.getStructures());
    spriteLayer.setSizes(xSize, ySize);
    spriteLayer.initializeAvatars(initialState.getAllAvatarLocations());
    spriteLayer.initializeBlocks(initialState.getAllBlockData());
  }

  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    spriteLayer.updateAvatarPosition(id, xCoord, yCoord);
  }

  public boolean updateAnimationForFrontEnd() {
    return spriteLayer.updateAnimationForFrontEnd();
  }

  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    spriteLayer.updateBlockPosition(id, xCoord, yCoord);
  }

  public void updateBlock(int id, boolean b) {
    spriteLayer.updateBlock(id, b);
  }

  public void setBlockNumber(int id, int newDisplayNum) {
    spriteLayer.setBlockNumber(id, newDisplayNum);
  }

  private void setSizing(GameGridData gameGridData) {
    ResourceBundle boardValues = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + BOARD_PROPERTIES);
    rows = gameGridData.getRows();
    cols = gameGridData.getColumns();
    double maxWidth = Double.parseDouble(boardValues.getString("gridXSize"));
    double maxHeight = Double.parseDouble(boardValues.getString("gridYSize"));
    int cellSide = (int) Math.floor(Math.min(maxHeight / rows, maxWidth / cols));
    xSize = cellSide;
    ySize = cellSide;
    gridXSize = xSize * cols;
    gridYSize = ySize * rows;
    gridLayer = new GridPane();
  }

  private void makeGrid(Structure[][] structures) {
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        Rectangle block = new Rectangle(xSize - 1, ySize - 1);
        block.getStyleClass().add("state");
        block.getStyleClass().add("state-" + structures[x][y].name().toLowerCase(Locale.ROOT));
        gridLayer.add(block, x, y);
      }
    }
  }

}
