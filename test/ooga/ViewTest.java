package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.Controller;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.LevelSelector;
import ooga.view.ScreenCreator;
import ooga.view.level.LevelView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class ViewTest extends ApplicationTest {

  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController;
  private ScreenCreator screenCreator;

  @Override
  public void start(Stage stage) throws Exception {
    Controller controller = new Controller(stage);
    viewController = (FrontEndExternalAPI)  getPrivateField(controller, "viewController");
    modelController = (BackEndExternalAPI)  getPrivateField(controller, "modelController");
    screenCreator = (ScreenCreator)  getPrivateField(viewController, "screenCreator");
  }

  @Test
  void testStartGame() {
    clickButton("start-button");
    Stage stage = (Stage) getPrivateField(screenCreator, "stage");
    assertTrue(stage.getScene().getRoot() instanceof LevelSelector);
  }

  @Test
  void testLoadLevel() {
    clickButton("start-button");
    clickButton("load-level-1");
    LevelView levelView = screenCreator.getLevelView();
    assertEquals(1, (int) getPrivateField(levelView, "level"));
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
