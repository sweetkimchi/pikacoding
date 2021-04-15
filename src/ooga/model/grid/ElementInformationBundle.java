package ooga.model.grid;

import com.google.api.Backend;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import ooga.controller.BackEndExternalAPI;
import ooga.model.Direction;
import ooga.model.InformationBundle;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.TileData;
import ooga.model.player.Avatar;
import ooga.model.player.AvatarData;
import ooga.model.player.DataCube;
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
  private final List<Block> dataCubeList;
  private Map<Integer, Integer> lineUpdates;
  private ElementData newUpdate;
  private BackEndExternalAPI modelController;
  private List<Integer> endCommandLines;
  private Map<Integer, Integer> mapOfCommandLines;

  public ElementInformationBundle() {
    avatarList = new ArrayList<>();
    dataCubeList = new ArrayList<>();
    lineUpdates = new HashMap<>();
    newUpdate = new AvatarData();
  }

  public List<Player> getAvatarList() {
    return Collections.unmodifiableList(avatarList);
  }

  public void setModelController(BackEndExternalAPI modelController){
    this.modelController = modelController;
  }

  public BackEndExternalAPI getModelController(){
    return modelController;
  }

  public List<BlockData> getBlockData() {
    List<BlockData> ret = new ArrayList<>();
    for (Block dataCube : dataCubeList) {
      ret.add(new BlockData(dataCube));
    }
    return ret;
  }


  public void setDimensions(int x, int y) {
    grid = new Tile[x][y];
    for (int i=0; i<x; i++) {
      for (int j=0; j<y; j++) {
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


  public void addGameElement(Element gameElement) {
    int xPos = gameElement.getXCoord();
    int yPos = gameElement.getYCoord();
    grid[xPos][yPos].add(gameElement);
    if (gameElement instanceof Avatar) {
      avatarList.add((Avatar) gameElement);
    }
    if (gameElement instanceof DataCube) {
      dataCubeList.add((DataCube) gameElement);
    }
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
    return grid[x][y];
  }

  public void setEndCommandLines(List<Integer> endCommandLines) {
    this.endCommandLines = endCommandLines;
  }

  public List<Integer> getEndCommandLines(){
    return this.endCommandLines;
  }

  public Map<Integer,Integer> getMapOfCommendLines() {
    return this.mapOfCommandLines;
  }

  public void setMapOfCommandLines(Map<Integer,Integer> mapOfCommandLines){
    this.mapOfCommandLines = mapOfCommandLines;
  }

  public Map<Integer,Integer> getMapOfCommandLines(){
    return this.mapOfCommandLines;
  }
}
