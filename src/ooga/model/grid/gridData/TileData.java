package ooga.model.grid.gridData;

import ooga.model.grid.Structure;
import ooga.model.grid.Tile;

public class TileData {

  public static final int EMPTY = -1;
  private Structure structure;
  private int avatarId = EMPTY;
  private int blockId = EMPTY;

  public TileData(Tile tile) {
    structure = tile.getStructure();
    if (tile.hasAvatar()) avatarId = tile.getAvatar().getId();
    if (tile.hasBlock()) blockId = tile.getBlock().getId();
  }

  public Structure getStructure() {
    return structure;
  }

  public int getAvatarId() {
    return avatarId;
  }

  public int getBlockId() {
    return blockId;
  }

  public boolean hasAvatar() {
    return avatarId != EMPTY;
  }

  public boolean hasBlock() {
    return blockId != EMPTY;
  }

}
