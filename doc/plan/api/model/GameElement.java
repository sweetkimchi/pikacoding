/**
 * GameElement is a generic type of element that is represented on screen on the Grid.
 *
 * @author Harrison Huang
 * @author Ji Yun Hyo
 */
public interface GameElement {

  /**
   * Sets the location of the game element.
   *
   * @param x The x-coordinate
   * @param y The y-coordinate
   */
  public void setLocation(int x, int y);

  public void setID(int id);
}