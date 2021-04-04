package ooga.model.grid;

import ooga.model.player.Avatar;
import ooga.model.player.Objects;
import ooga.model.player.Structure;

/**
 * A Tile is one tile of the grid that can contain some number of elements.
 *
 * @author Harrison Huang
 */
public class Tile {

  Structure structure;
  Avatar avatar;
  Objects block;

  public Tile() {

  }

  //TODO: implement reflection here? needs better design
  public void add(Structure structure) {
    if (canAddStructure()) this.structure = structure;
  }

  public void add(Avatar avatar) {
    if (canAddAvatar()) this.avatar = avatar;
  }

  public void add(Objects block) {
    if (canAddBlock()) this.block = block;
  }

  public boolean canAddStructure() {
    return structure == null;
  }

  public boolean canAddAvatar() {
    return avatar == null;
  }

  public boolean canAddBlock() {
    return block == null;
  }
  //TODO: improve this too (maybe try changing from ArrayList to guarantee ordering?)
  public Avatar getAvatar() {
    return avatar;
  }

  public Objects getObject() {
    return block;
  }

  public void removeAvatar() {
    avatar = null;
  }

  public void removeBlock() {
    block = null;
  }

}
