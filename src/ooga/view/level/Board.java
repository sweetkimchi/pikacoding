package ooga.view.level;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import ooga.model.grid.Structure;
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
    setSizing(gameGridData);
    spriteLayer = new SpriteLayer(gridXSize, gridYSize);
    this.getChildren().addAll(gridLayer, spriteLayer);
    gridLayer.getStyleClass().add("board");
    makeGrid(gameGridData.getStructures());
    spriteLayer.setSizes(xSize, ySize);
    spriteLayer.initializeAvatars(initialState.getAllAvatarLocations());
    spriteLayer.initializeBlocks(initialState.getAllBlockData());
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
        Rectangle block = new Rectangle(xSize, ySize);
        block.getStyleClass().add("state");
        block.getStyleClass().add("state-" + structures[x][y].name().toLowerCase(Locale.ROOT));
        gridLayer.add(block, x, y);
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
