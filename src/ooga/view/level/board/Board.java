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

/**
 * Area of the level view that displays the board.
 * Board includes both the SpriteLayer and the grid.
 *
 * @author Kathleen Chen
 * @author David Li
 */

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

  /**
   * Main constructor.
   */
  public Board() {
  }

  /**
   * Resets the board and all the elements on the board
   */
  public void reset() {
    spriteLayer.resetAvatarLocations();
    spriteLayer.resetBlockData();
    spriteLayer.resetAnimationQueue();
    spriteLayer.resetAvatarImages();
  }

  /**
   * Initializes the board based on grid data.
   * Creates the game grid based on the grid data.
   * Creates a SpriteLayer and adds the necessary avatars and blocks onto the layer.
   * @param gameGridData GameGridData that holds information concerning the way the grid should be constructed
   * @param initialState InitialState that holds information of the initial states of the avatars and blocks
   */
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

  /**
   * Updates the positions of the avatars based on id and new coordinates
   * @param id int representing the id of the avatar that needs to be updated
   * @param xCoord new x coordinate int value
   * @param yCoord new y coordinate int value
   */
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    spriteLayer.updateAvatarPosition(id, xCoord, yCoord);
  }

  /**
   * Returns a boolean of whether or not the front end animation is finished or not.
   * @return boolean that tells if the front end animation is done running
   */
  public boolean updateAnimationForFrontEnd() {
    return spriteLayer.updateAnimationForFrontEnd();
  }

  /**
   * Updates the position of a block based on its id and new coordinates.
   * @param id int representing the id of the block that needs to be updated
   * @param xCoord new x coordinate int value
   * @param yCoord new y coordinate int value
   */
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    spriteLayer.updateBlockPosition(id, xCoord, yCoord);
  }

  /**
   * Updates if the block is being held or not.
   * @param id int representing the id of the block in question
   * @param b boolean representing if the block is being held or not
   */
  public void updateBlock(int id, boolean b) {
    spriteLayer.updateBlock(id, b);
  }

  /**
   * Changes the number on a specific block.
   * @param id int representing the id of the block in question
   * @param newDisplayNum int representing the new number to be displayed on the block
   */
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

  /**
   * Resets the animation queue.
   */
  public void resetAnimation() {
    spriteLayer.resetAnimationQueue();
  }
}
