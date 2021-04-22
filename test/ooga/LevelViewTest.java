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
import ooga.controller.FrontEndExternalAPI;
import ooga.view.level.ViewAvatar;
import ooga.view.level.LevelView;
import ooga.view.level.SpriteLayer;
import ooga.view.level.codearea.ProgramStack;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class LevelViewTest extends ApplicationTest {

  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController;
  private LevelView levelView;
  private ProgramStack programStack;
  private Map<Integer, ViewAvatar> avatars;

  @Override
  public void start(Stage stage) throws Exception {
    Controller controller = new Controller(stage);
    viewController = (FrontEndExternalAPI)  getPrivateField(controller, "viewController");
    modelController = (BackEndExternalAPI)  getPrivateField(controller, "modelController");
    viewController.initializeLevel(1);
    levelView = (LevelView)  getPrivateField(viewController, "levelView");
    avatars = (Map<Integer, ViewAvatar>) getPrivateField(lookup("#sprite-layer").queryAs(SpriteLayer.class), "avatars");
    programStack = lookup("#program-stack").queryAs(ProgramStack.class);
  }

  @Test
  void testAddCommandBlock() {
    clickButton("drop-option-button");
    assertEquals("drop", programStack.getProgram().get(0).getType());
  }

  @Test
  void testRemoveCommandBlock() {
    clickButton("drop-option-button");
    assertEquals(1, programStack.getProgram().size());
    clickButton("remove-button-1");
    assertEquals(0, programStack.getProgram().size());
  }

  @Test
  void testAvatarMovement() {
    ImageView avatarImage = (ImageView) getPrivateField(avatars.get(7), "avatar");
    double initialY = avatarImage.getY();
    clickButton("step-option-button");
    clickButton("Button4_Step-button");
    assertTrue(initialY > avatarImage.getY());
  }

  @Test
  void testPauseButton() {
    clickButton("pause-button");
    assertTrue(levelView.getBottom() == null);
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

  private void clickButton(String button) {
    clickOn(lookup("#" + button).queryButton());
    Platform.runLater(() -> lookup("#" + button).queryButton().fire());
    sleep(100);
  }

}
