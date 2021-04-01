/**
 * Blocks are non-player elements that appear on screen. It is intended to be used for datacubes,
 * a type of object that avatars can interact with and manipulate.
 *
 * @author Harrison Huang
 * @author Ji Yun Hyo
 */
public interface Block {

  /**
   * The block is picked up by an avatar.
   */
  public void getsPickedUp();

  /**
   * The block is dropped by an avatar.
   */
  public void getsDropped();

  /**
  * Returns the id of the avatar
  */
  public int getID();
}