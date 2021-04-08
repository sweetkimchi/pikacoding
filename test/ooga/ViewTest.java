package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.application.Platform;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.level.codearea.ProgramStack;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class ViewTest extends ApplicationTest {

  private ProgramStack programStack;

  @Override
  public void start(Stage stage) throws Exception {
    Controller controller = new Controller(stage);
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
    assertEquals("drop", programStack.getProgram().get(0).getType());
  }

}
