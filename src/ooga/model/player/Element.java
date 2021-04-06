package ooga.model.player;

/**
 * An element is the highest level abstraction of a tile in the Grid. Each element has an ID.
 *
 * @author Harrison Huang
 */
public interface Element {
  
  int programCounter = 1;
  /**
   * Getter for the ID of the element.
   *
   * @return The ID of the element
   */
  int getId();

}