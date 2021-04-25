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
   * Purpose: returns the list of avatars so that CommandExecutor can execute each of the commands on each of the
   * avatars
   * Assumptions: list of Avatars is properly constructed in the initial parser for the level
   * @return the list of Avatars
   */
  List<Player> getAvatarList();

  /**
   * Purpose: passes in the model controller which handles the communication between frontend and backend
   * Assumption: modelController is not null
   * @param modelController an implementation of BackEndExternalAPI
   */
  void setModelController(BackEndExternalAPI modelController);

  /**
   * Purpose: returns the model controller instance so that eah class can notify the frontend of any updates
   * Assumption: modelController is not null
   * Exceptions: sometimes the modelController may be null if any of its commands are called before the level view
   * is initialized
   * @return modelController object
   */
  BackEndExternalAPI getModelController();

  /**
   * Purpose: returns the list of blockdata
   * Assumption: block data is appropriately constructed in InitialParser
   * Exception: null exception if there are no blocks
   * @return list of blocks
   */
  List<BlockData> getBlockData();
  void setDimensions(int x, int y);
  Structure getStructure(int x, int y);
  void setStructure(int x, int y, Structure structure);
  void addAvatar(Avatar avatar);
  void addBlock(Block block);
  Player getAvatarById(int id);
  Collection<Integer> getAvatarIds();
  TileData getTileData(int x, int y);
  void setEndCommandLines(List<Integer> endCommandLines);
  Tile getTile(int x, int y);
  void setMapOfCommandLines(Map<Integer, Integer> mapOfCommandLines);
  Map<Integer, Integer> getMapOfCommandLines();
}
