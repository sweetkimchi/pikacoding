package ooga.model.player;

/**
 * 
 */
public abstract class Block implements Element {

  private boolean isHeld;

  /**
   * Default constructor
   */
  public Block() {
    isHeld = false;
  }

  /**
   * Gets the number associated with the
   * @return
   */
  public abstract int getDisplayNum();

  public void drop() {
    isHeld = false;
  }

  public void pickUp() {
    isHeld = true;
  }

  public boolean isHeld() {
    return isHeld;
  }

}