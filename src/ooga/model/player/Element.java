package ooga.model.player;

/**
 * An element is the highest level abstraction of a tile in the Grid. Each element has an ID.
 *
 * @author Harrison Huang
 */
public interface Element {

  /**
   * Getter for the ID of the element.
   *
   * @return The ID of the element
   */
  int getId();

  /**
   * Getter for the x-coordinate of the Element.
   *
   * @return The x-coordinate of the Element
   */
  int getXCoord();

  /**
   * Getter for the y-coordinate of the Element.
   *
   * @return The y-coordinate of the Element
   */
  int getYCoord();

  /**
   * Updates the X and Y coordinates of the Element.
   *
   * @param xCoord The new x-coordinate
   * @param yCoord The new y-coordinate
   */
  void setXY(int xCoord, int yCoord);

}