package ooga.model.player;

/**
 * The Avatar is a type of Player directed by the player of the game able to pick up and drop
 * blocks. The Avatar can only hold one block at a time.
 *
 * @author Harrison Huang
 */
public class Avatar extends Player {

  private final int id;
  private Block heldItem;
  private int xCoord;
  private int yCoord;

  /**
   * Default constructor
   */
  public Avatar(int id, int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.id = id;
    heldItem = null;
  }

  /**
   * Getter for the ID of the element.
   *
   * @return The ID of the element
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Getter for the x-coordinate of the Element.
   *
   * @return The x-coordinate of the Element
   */
  @Override
  public int getXCoord() {
    return xCoord;
  }

  /**
   * Getter for the y-coordinate of the Element.
   *
   * @return The y-coordinate of the Element
   */
  @Override
  public int getYCoord() {
    return yCoord;
  }

  /**
   * Updates the X and Y coordinates of the Element.
   *
   * @param xCoord The new x-coordinate
   * @param yCoord The new y-coordinate
   */
  @Override
  public void setXY(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  /**
   * Directs the avatar to pick up a block.
   *
   * @param block The block to pick up
   */
  @Override
  public void pickUp(Block block) {
    heldItem = block;
  }

  /**
   * Directs the avatar to drop the block it is holding. Returns the block it dropped.
   *
   * @return The block that was dropped
   */
  @Override
  public Block drop() {
    Block ret = heldItem;
    heldItem = null;
    return ret;
  }

  /**
   * Returns the boolean of whether or not the avatar is holding a block.
   *
   * @return The boolean if the avatar has a block
   */
  public boolean hasBlock() {
    return heldItem != null;
  }

  /**
   * Returns the block that the avatar is holding.
   *
   * @return The block that the avatar is holding
   */
  public Block getHeldItem() {
    return heldItem;
  }
}