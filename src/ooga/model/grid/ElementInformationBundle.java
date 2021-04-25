package ooga.model.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.TileData;
import ooga.model.player.Avatar;
import ooga.model.player.AvatarData;
import ooga.model.player.Element;
import ooga.model.player.Block;
import ooga.model.player.ElementData;
import ooga.model.player.Player;

/**
 * @author Ji Yun Hyo
 */
public class ElementInformationBundle implements InformationBundle {

  private Tile[][] grid;
  private final List<Player> avatarList;
  private final List<Block> blockList;
  private Map<Integer, Integer> lineUpdates;
  private ElementData newUpdate;
  private BackEndExternalAPI modelController;
  private List<Integer> endCommandLines;
  private Map<Integer, Integer> mapOfCommandLines;

  public ElementInformationBundle() {
    avatarList = new ArrayList<>();
    blockList = new ArrayList<>();
    lineUpdates = new HashMap<>();
    newUpdate = new AvatarData();
  }

  /**
   * Purpose: returns the list of avatars so that CommandExecutor can execute each of the commands on each of the
   * avatars
   * Assumptions: list of Avatars is properly constructed in the initial parser for the level
   * @return the list of Avatars
   */
  public List<Player> getAvatarList() {
    return Collections.unmodifiableList(avatarList);
  }

  /**
   * Purpose: passes in the model controller which handles the communication between frontend and backend
   * @param modelController an implementation of BackEndExternalAPI
   */
  public void setModelController(BackEndExternalAPI modelController) {
    this.modelController = modelController;
  }

  /**
   * Purpose: returns the model controller instance so that eah class can notify the frontend of any updates
   * Assumption: modelController is not null
   * @return modelController object
   */
  public BackEndExternalAPI getModelController() {
    return modelController;
  }

  /**
   * Purpose: returns the list of blockdata
   * Assumption: block data is appropriately constructed in InitialParser
   * Exception: null exception if there are no blocks
   * @return list of blocks
   */
  public List<BlockData> getBlockData() {
    List<BlockData> ret = new ArrayList<>();
    for (Block dataCube : blockList) {
      ret.add(new BlockData(dataCube));
    }
    return ret;
  }


  public void setDimensions(int x, int y) {
    grid = new Tile[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        grid[i][j] = new Tile();
      }
    }
  }

  public Structure getStructure(int x, int y) {
    return grid[x][y].getStructure();
  }

  public void setStructure(int x, int y, Structure structure) {
    grid[x][y].setStructure(structure);
  }

  public void addAvatar(Avatar avatar) {
    int xPos = avatar.getXCoord();
    int yPos = avatar.getYCoord();
    grid[xPos][yPos].add(avatar);
    avatarList.add(avatar);
  }

  public void addBlock(Block block) {
    int xPos = block.getXCoord();
    int yPos = block.getYCoord();
    grid[xPos][yPos].add(block);
    blockList.add(block);
  }


  public Player getAvatarById(int id) {
    for (Player avatar : avatarList) {
      if (avatar.getId() == id) {
        return avatar;
      }
    }
    return null; // should never happen
  }

  /**
   * Returns a collection of the IDs of all the current avatars.
   *
   * @return A collection of integers containing IDs
   */
  public Collection<Integer> getAvatarIds() {
    List<Integer> ids = new ArrayList<>();
    for (Element avatar : avatarList) {
      ids.add(avatar.getId());
    }
    Collections.sort(ids);
    return ids;
  }

  /**
   * Retrieves the information of a queried Tile as a TileData object.
   *
   * @param x The x-coordinate of the tile
   * @param y The y-coordinate of the tile
   * @return A TileData object containing information about the tile
   */
  public TileData getTileData(int x, int y) {
    return new TileData(grid[x][y]);
  }

  public Tile getTile(int x, int y) {
    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
      return null;
    }
    return grid[x][y];
  }

  public void setEndCommandLines(List<Integer> endCommandLines) {
    this.endCommandLines = endCommandLines;
  }

  public void setMapOfCommandLines(Map<Integer, Integer> mapOfCommandLines) {
    this.mapOfCommandLines = mapOfCommandLines;
  }

  public Map<Integer, Integer> getMapOfCommandLines() {
    return this.mapOfCommandLines;
  }
}
