package ooga;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.Controller;
import ooga.view.level.Avatar;
import ooga.view.level.SpriteLayer;
import ooga.view.level.codearea.ProgramStack;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class ViewTest extends ApplicationTest {

  private BackEndExternalAPI modelController;
  private ProgramStack programStack;
  private Map<Integer, Avatar> avatars;

  @Override
  public void start(Stage stage) throws Exception {
    Controller controller = new Controller(stage);
    modelController = (BackEndExternalAPI)  getPrivateField(controller, "modelController");
    avatars = (Map<Integer, Avatar>) getPrivateField(lookup("#sprite-layer").queryAs(SpriteLayer.class), "avatars");
    programStack = lookup("#program-stack").queryAs(ProgramStack.class);
  }

  @Test
  void testAddCommandBlock() {
    clickOn(lookup("#drop-option-button").queryButton());
    Platform.runLater(() -> lookup("#drop-option-button").queryButton().fire());
    sleep(100);
    assertEquals("drop", programStack.getProgram().get(0).getType());
  }

  @Test
  void testRemoveCommandBlock() {
    clickOn(lookup("#drop-option-button").queryButton());
    Platform.runLater(() -> lookup("#drop-option-button").queryButton().fire());
    sleep(100);
    assertEquals(1, programStack.getProgram().size());
    clickOn(lookup("#remove-button-1").queryButton());
    Platform.runLater(() -> lookup("#remove-button-1").queryButton().fire());
    sleep(100);
    assertEquals(0, programStack.getProgram().size());
  }

  @Test
  void testAvatarMovement() {
    ImageView avatarImage = (ImageView) getPrivateField(avatars.get(7), "avatar");
    double initialY = avatarImage.getY();
    clickOn(lookup("#step-option-button").queryButton());
    Platform.runLater(() -> lookup("#step-option-button").queryButton().fire());
    sleep(100);
    clickOn(lookup("#Button4_Step-button").queryButton());
    Platform.runLater(() -> lookup("#Button4_Step-button").queryButton().fire());
    sleep(1000);
    assertTrue(initialY > avatarImage.getY());
  }

  private Object getPrivateField(Object object, String field) {
    try {
      Field f = object.getClass().getDeclaredField(field);
      f.setAccessible(true);
      return f.get(object);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      fail("Can't access field");
      return null;
    }
  }

}
