package ooga.view.animation;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ooga.controller.FrontEndExternalAPI;

public class AnimationPane {

  private FrontEndExternalAPI viewController;
  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private static final String DEFAULT_RESOURCES =
      AnimationPane.class.getPackageName() + ".resources.";
  private static final String UPDATE_NEXT_RESOURCE =
      DEFAULT_RESOURCES + "UpdateNextReflectionActions";



  public AnimationPane(FrontEndExternalAPI viewController){
    this.viewController = viewController;
    commandsToBeExecuted = new ArrayDeque<>();
    commandsToBeExecuted = new ArrayDeque<>();
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
        Method m = AnimationPane.this.getClass().getDeclaredMethod(methodName);
        m.invoke(AnimationPane.this);
      } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR);
      }
    }
  }

  private void updatePosition() {
    double nextX = commandsToBeExecuted.pop();
    double nextY = commandsToBeExecuted.pop();

    System.out.println("updatePosition called");
  }

}
