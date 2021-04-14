package ooga.model.player;

import ooga.model.Direction;

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
   * @return xCoordinate of the Element
   */
  int getXCoord();

  /**
   * @return yCoordinate of the Element
   */
  int getYCoord();

  /**
   * update the xCoordinate
   */
  void setXCoord(int xCoord);

  /**
   * update the yCoordinate
   */
  void setYCoord(int yCoord);



  void step(Direction direction);
}