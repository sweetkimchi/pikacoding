package ooga.model.player;

/**
 * The Block is a general abstraction for a non-player object in the game. It has an associated
 * display number and can be set as held or not held.
 *
 * @author Harrison Huang
 */
public abstract class Block implements Element {

  private boolean isHeld;

  /**
   * Default constructor of Block. Initialized to be not held.
   */
  public Block() {
    isHeld = false;
  }

  /**
   * Gets the number associated with the block. To be overridden by the extending classes.
   *
   * @return The number associated with the block
   */
  public abstract int getDisplayNum();

  /**
   * Runs the behavior when the block is dropped. Sets the isHeld boolean to be false.
   */
  public void drop() {
    isHeld = false;
  }

  /**
   * Runs the behavior when the block is picked up. Sets the isHeld boolean to be true.
   */
  public void pickUp() {
    isHeld = true;
  }

  /**
   * Returns the boolean for whether the block is held or not.
   *
   * @return The boolean if the block is held
   */
  public boolean isHeld() {
    return isHeld;
  }

}