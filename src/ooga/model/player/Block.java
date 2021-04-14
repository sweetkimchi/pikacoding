package ooga.model.player;

/**
 * 
 */
public abstract class Block implements Element {

  private boolean isHeld;
  private static final int EMPTY = -1;

  private int holderId = EMPTY;
    /**
     * Default constructor
     */
    public Block() {
      isHeld = false;
    }

  public abstract int getDisplayNum();

  public void drop() {
    holderId = EMPTY;
    isHeld = false;
  }

  public void pickUp(int id) {
    holderId = id;
    isHeld = true;
  }

  public boolean isHeld() {
    return isHeld;
  }
}