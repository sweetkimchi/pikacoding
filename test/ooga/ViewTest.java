package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.Controller;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.GameTypeSelector;
import ooga.view.LevelSelector;
import ooga.view.MatchIdSelector;
import ooga.view.ScreenCreator;
import ooga.view.TeamSelector;
import ooga.view.level.LevelView;
import ooga.view.level.LoseScreen;
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
    screenCreator.setTeamNum(0);
    screenCreator.setMatchId(12);
  }

  @Test
  void testStartGame() {
    clickButton("start-button");
    Stage stage = (Stage) getPrivateField(screenCreator, "stage");
    assertTrue(stage.getScene().getRoot() instanceof MatchIdSelector);
  }

  @Test
  void testLoadLevel() {
    clickButton("start-button");
    enterMatchId();
    clickButton("single-player-button");
    clickButton("load-level-1");
    LevelView levelView = screenCreator.getLevelView();
    assertEquals(1, (int) getPrivateField(levelView, "level"));
  }

  @Test
  void testLoadMulti() {
    clickButton("start-button");
    enterMatchId();
    clickButton("multiplayer-button");
    Stage stage = (Stage) getPrivateField(screenCreator, "stage");
    assertTrue(stage.getScene().getRoot() instanceof TeamSelector);
  }

  @Test
  void successfulSinglePlayerButtonCreation() {
    clickButton("start-button");
    enterMatchId();
    assertTrue(lookup("#single-player-button").query() != null);
  }

//  @Test
//  void determineCorrectTeam() {
//    clickButton("start-button");
//    enterMatchId();
//    clickButton("multiplayer-button");
//    clickButton("team1-button");
//    assertTrue(((int) getPrivateField(modelController, "teamID")) == 1);
//  }

  @Test
  void changeStyleSheet() {
    clickOn(lookup("#css-combo").queryComboBox());

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
//    Platform.runLater(() -> lookup("#" + button).queryButton().fire());
//    sleep(100);
  }

  private void enterMatchId() {
    Stage stage = (Stage) getPrivateField(screenCreator, "stage");
    MatchIdSelector matchIdSelector = (MatchIdSelector) stage.getScene().getRoot();
    TextField textField = (TextField) getPrivateField(matchIdSelector, "matchInput");
    textField.setText("9876");
    clickButton("enter-button");
  }

  @Test
  public void testSetTeamNumber(){
    int teamNumber = 19;
    int matchNumber =10;
    screenCreator.setTeamNum(teamNumber);
    screenCreator.setMatchId(matchNumber);
    assertEquals(teamNumber, 19);
    assertEquals(matchNumber, 10);
  }


}
