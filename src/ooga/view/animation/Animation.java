package ooga.view.animation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * This class takes the information and processes it to little chunks so that each of the chunk
 * can be assigned a specific animation state. Although there are other ways of doing this, the
 * current implementation was chosen to allow maximum flexibility in terms of how much the animation
 * can be customized.
 * @author Ji Yun Hyo
 */
public class Animation {

  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private Map<Integer, Deque<Double>> allElementInformation;

  /**
   * Initializes the animation queues
   */
  public Animation(){
    commandsToBeExecuted = new ArrayDeque<>();
    allElementInformation = new HashMap<>();
    typeToBeUpdated = new ArrayDeque<>();
  }

  /**
   * Returns all element position and state information. This method is called by the SpriteLayer
   * to access the animation data contained in this class
   * @return map of all element update information
   */
  public Map<Integer, Deque<Double>> getAllElementInformation(){
    return allElementInformation;
  }

  /**
   *
   * @param id ID of the element
   * @param initialX initial x position of the element
   * @param initialY initial y position of the element
   * @param xCoord new x position of the element
   * @param yCoord new y position of the element
   */
  public void queuePositionUpdates(int id, int initialX, int initialY, int xCoord, int yCoord) {
    double INCREMENT_FACTOR = 30;
    double xIncrement = (xCoord - initialX)/ INCREMENT_FACTOR;
    double yIncrement = (yCoord - initialY)/ INCREMENT_FACTOR;
    Deque<Double> position = new ArrayDeque<>();
    for (int i = 1; i <= INCREMENT_FACTOR; i++) {
      position.add(initialX + xIncrement * i);
      position.add(initialY + yIncrement * i);
      typeToBeUpdated.add("Positions");
    }
    allElementInformation.put(id, position);
  }

  /**
   * Resets the animation by clearing all the queues
   */
  public void reset() {
    allElementInformation.clear();
    typeToBeUpdated.clear();
    commandsToBeExecuted.clear();
  }
}
