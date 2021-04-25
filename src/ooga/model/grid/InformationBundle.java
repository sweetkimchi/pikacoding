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
   *
   * @return
   */
  List<Player> getAvatarList();
  void setModelController(BackEndExternalAPI modelController);
  BackEndExternalAPI getModelController();
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
