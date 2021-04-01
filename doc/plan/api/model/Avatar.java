/**
 * Avatar is a generic character that is shown on screen. It can execute commands given to it. It
 * is intended to be used by the Human, a type of avatar that can execute basic player commands.
 *
 * @author Harrison Huang
 * @author Ji Yun Hyo
 */
public interface Avatar {

  /**
   * Moves the avatar in a cardinal direction.
   *
   * @param direction The direction to be moved
   */
  public void step(Direction direction);

  /**
   * Directs the avatar to pick up a block from one of its specified directions.
   *
   * @param direction The direction to attempt to pick up a block
   */
  public void pickUp(Direction direction, Element id);

  /**
   * Directs the avatar to drop the block it is holding.
   */
  public void drop(Element id);

  /**
  * Returns the id of the avatar
  */
  public int getID();

}