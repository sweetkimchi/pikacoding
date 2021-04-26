package ooga.model.grid;

import ooga.model.player.Avatar;
import ooga.model.player.Block;

/**
 * A Tile is one tile of the grid that can contain some number of elements.
 *
 * @author Harrison Huang
 */
public class Tile {

  private Structure structure;
  private Avatar avatar;
  private Block block;

  public Tile() {

  }

  public void setStructure(Structure structure) {
    this.structure = structure;
  }

  public void add(Avatar avatar) {
    if (canAddAvatar()) {
      this.avatar = avatar;
    }
  }

  public void add(Block block) {
    if (canAddBlock()) {
      this.block = block;
    }
  }

  public boolean canAddAvatar() {
    return (structure == Structure.FLOOR) && (avatar == null);
  }

  public boolean canAddBlock() {
    return (structure == Structure.FLOOR) && (block == null);
  }

  public Structure getStructure() {
    return structure;
  }

  public Avatar getAvatar() {
    return avatar;
  }

  public Block getBlock() {
    return block;
  }

  public void removeAvatar() {
    avatar = null;
  }

  public void removeBlock() {
    block = null;
  }

  public boolean hasBlock() {
    return block != null;
  }

  public boolean hasAvatar() {
    return avatar != null;
  }

}
