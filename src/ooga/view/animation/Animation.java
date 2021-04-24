package ooga.view.animation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ji Yun Hyo
 */
public class Animation {

  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private Map<Integer, Deque<Double>> allElementInformation;

  public Animation(){
    commandsToBeExecuted = new ArrayDeque<>();
    allElementInformation = new HashMap<>();
    typeToBeUpdated = new ArrayDeque<>();
  }

  public Map<Integer, Deque<Double>> getAllElementInformation(){
    return allElementInformation;
  }


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

  public void reset() {
    allElementInformation.clear();
    typeToBeUpdated.clear();
    commandsToBeExecuted.clear();
  }
}
