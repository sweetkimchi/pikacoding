package ooga.model.grid;

import java.rmi.StubNotFoundException;
import ooga.model.player.Avatar;
import ooga.model.player.Element;
import ooga.model.player.Objects;
import ooga.model.player.Structure;

/**
 * A Tile is one tile of the grid that can contain some number of elements.
 *
 * @author Harrison Huang
 */
public class Tile {

  private Structure structure;
  private Avatar avatar;
  private Objects block;

  public Tile() {

  }

  //TODO: implement reflection here? needs better design

  public void add(Element element) {
    if (element instanceof Structure) {
      add((Structure) element);
    }
    if (element instanceof Avatar) {
      add((Avatar) element);
    }
    if (element instanceof Objects) {
      add((Objects) element);
    }
  }

  public void add(Structure structure) {
    if (canAddStructure()) {
      this.structure = structure;
    }
  }

  public void add(Avatar avatar) {
    if (canAddAvatar()) {
      this.avatar = avatar;
    }
  }

  public void add(Objects block) {
    if (canAddBlock()) {
      this.block = block;
    }
  }

  public boolean canAddStructure() {
    return structure == null;
  }

  public boolean canAddAvatar() {
    return (structure == null) && (avatar == null);
  }

  public boolean canAddBlock() {
    return (structure == null) && (block == null);
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

  public boolean hasBlock() {
    return block != null;
  }

  public boolean hasAvatar() {
    return avatar != null;
  }

}