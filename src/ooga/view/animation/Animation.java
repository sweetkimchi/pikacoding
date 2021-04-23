package ooga.view.animation;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import ooga.controller.BackEndExternalAPI;
import ooga.model.Direction;
import ooga.model.player.Avatar;
import ooga.model.player.Element;

public class Animation {

  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private Map<Integer, Deque<Double>> allElementInformation;


  private static final String DEFAULT_RESOURCES =
      Animation.class.getPackageName() + ".resources.";
  private static final String UPDATE_NEXT_RESOURCE =
      DEFAULT_RESOURCES + "UpdateNextReflectionActions";
  private double INCREMENT_FACTOR = 30;
  private static final String PANE_BOX_ID = "AvatarView";

  public Animation(){
    commandsToBeExecuted = new ArrayDeque<>();
    allElementInformation = new HashMap<>();
    typeToBeUpdated = new ArrayDeque<>();
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    typeToBeUpdated.add(commandType);
    commandsToBeExecuted.addAll(commandValues);
  }

  private void clearQueue() {
    commandsToBeExecuted.clear();
    typeToBeUpdated.clear();
  }

  public void updateAvatarStates() {
    String key;
    ResourceBundle updateNextActionResources = ResourceBundle.getBundle(UPDATE_NEXT_RESOURCE);
    if (!typeToBeUpdated.isEmpty()) {
      key = typeToBeUpdated.removeFirst();
      try {
        String methodName = updateNextActionResources.getString(key);
        Method m = Animation.this.getClass().getDeclaredMethod(methodName);
        m.invoke(Animation.this);
      } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR);
      }
    }
  }

   /**
   * This is the method in which we add the incremented x,y positions into the queue for update later
   * @param x
   * @param y
   */
  public void setPosition(double x, double y) {
    int increment = 10;

  }

  private void setID() {
  }

  //
  public void setActiveAvatar(int avatarID) {

    commandsToBeExecuted.add((double) avatarID);
    typeToBeUpdated.add("SetID");
  }

  public void createAvatar(int id, Element element){

    // System.out.println(allElementInformation);
  }

  public Map<Integer, Deque<Double>> getAllElementInformation(){

//    System.out.println("All element information: " + allElementInformation);
    return allElementInformation;
  }


  public void moveAvatar(Avatar dummy, Direction direction) {

    int xPrev = dummy.getXCoord();
    int yPrev = dummy.getYCoord();
    dummy.setXCoord(dummy.getXCoord() + direction.getXDel());
    dummy.setYCoord(dummy.getYCoord() + direction.getYDel());




  }

  public void queuePositionUpdates(int id, int initialX, int initialY, int xCoord, int yCoord) {
    double xIncrement = (xCoord - initialX)/ INCREMENT_FACTOR;
    double yIncrement = (yCoord - initialY)/INCREMENT_FACTOR;
    Deque<Double> position = new ArrayDeque<>();
    for (int i = 1; i <= INCREMENT_FACTOR; i++) {
      position.add(initialX + xIncrement * i);
      position.add(initialY + yIncrement * i);
      typeToBeUpdated.add("Positions");
    }
    allElementInformation.put(id, position);


//    System.out.println(yCoord);
//    System.out.println(initialY);
//    System.out.println(allElementInformation);
  }

  public void reset() {
    allElementInformation.clear();
    typeToBeUpdated.clear();
    commandsToBeExecuted.clear();
  }
}
