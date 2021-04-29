package ooga.model.grid;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.TileData;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.Player;

/**
 * @author Ji Yun Hyo
 */
public interface InformationBundle {

  /**
   * Returns the list of avatars so that CommandExecutor can execute each of the commands
   * on each of the avatars Assumptions: list of Avatars is properly constructed in the initial
   * parser for the level
   *
   * @return the list of Avatars
   */
  List<Player> getAvatarList();

  /**
   * Passes in the model controller which handles the communication between frontend and
   * backend Assumption: modelController is not null
   *
   * @param modelController an implementation of BackEndExternalAPI
   */
  void setModelController(BackEndExternalAPI modelController);

  /**
   * Returns the model controller instance so that eah class can notify the frontend of any
   * updates Assumption: modelController is not null Exceptions: sometimes the modelController may
   * be null if any of its commands are called before the level view is initialized
   *
   * @return modelController object
   */
  BackEndExternalAPI getModelController();

  /**
   * Returns the list of blockdata Assumption: block data is appropriately constructed in
   * InitialParser Exception: null exception if there are no blocks
   *
   * @return list of blocks
   */
  List<BlockData> getBlockData();

  /**
   * Sets the dimensions of the grid according to the template
   *
   * @param x row size
   * @param y col size
   */
  void setDimensions(int x, int y);

  /**
   * Returns the structure at the location
   *
   * @param x x coordinate on the grid
   * @param y y coordinate on the grid
   * @return the structure at the grid location
   */
  Structure getStructure(int x, int y);

  /**
   * Sets the structure at a specific location (e.g. wall, floor, etc) Assumption: x and y
   * are valid locations within the grid
   *
   * @param x         x coordinate on the grid
   * @param y         y coordinate on the grid
   * @param structure structure to be added to the location
   */
  void setStructure(int x, int y, Structure structure);

  /**
   * Adds avatar to the location during initializtion of the level
   *
   * @param avatar Avatar to be added to the tile
   */
  void addAvatar(Avatar avatar);

  /**
   * Adds a block at the designated location during initialization of the level
   *
   * @param block block to be added to the location
   */
  void addBlock(Block block);

  /**
   * Retrieves the avatar object by its ID
   *
   * @param id ID of the avatar
   * @return Avatar object
   */
  Player getAvatarById(int id);

  /**
   * Returns a collection of the IDs of all the current avatars. Assumptions: list of avatars is not
   * null
   *
   * @return A collection of integers containing IDs
   */
  Collection<Integer> getAvatarIds();

  /**
   * Retrieves the information of a queried Tile as a TileData object.
   *
   * @param x The x-coordinate of the tile
   * @param y The y-coordinate of the tile
   * @return A TileData object containing information about the tile
   */
  TileData getTileData(int x, int y);

  /**
   * Keeps track of if/endif pair so that commands can jump to the designated program
   * counter Assumption: if/endif exists
   *
   * @param endCommandLines list of endCommands
   */
  void setEndCommandLines(List<Integer> endCommandLines);

  /**
   * Returns the specific tile at the coordinate
   *
   * @param x The x-coordinate of the tile
   * @param y The y-coordinate of the tile
   * @return tile object
   */
  Tile getTile(int x, int y);

  /**
   * Keep track of all commands associated with each of the command lines
   *
   * @param mapOfCommandLines map containing line number and the command at the line
   */
  void setMapOfCommandLines(Map<Integer, Integer> mapOfCommandLines);

  /**
   * Returns the map of command lines so that CommandExecutor can execute commands
   *
   * @return map of commandlines
   */
  Map<Integer, Integer> getMapOfCommandLines();
}
