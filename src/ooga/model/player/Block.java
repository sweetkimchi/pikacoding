package ooga.model.player;

/**
 * 
 */
public abstract class Block implements Element {

    /**
     * Default constructor
     */
    public Block() {
    }

  public abstract int getDisplayNum();

  public abstract boolean isHeld();
}