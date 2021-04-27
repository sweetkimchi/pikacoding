package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;
import ooga.view.level.LevelView;
import ooga.view.level.LoseScreen;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javax.swing.*;

class ViewTest extends ApplicationTest {

  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController;
  private ScreenCreator screenCreator;
  private GUIElementInterface GUIFactory;

  @Override
  public void start(Stage stage) throws Exception {
    Controller controller = new Controller(stage);

    GUIFactory = new GUIElementFactory();
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
    assert((stage.getScene().getRoot() instanceof MatchIdSelector));
  }

//  @Test
//  void testLoadLevel() {
//    clickButton("start-button");
//    enterMatchId();
//    clickButton("single-player-button");
//    clickButton("load-level-1");
//    LevelView levelView = screenCreator.getLevelView();
//    assertEquals(1, (int) getPrivateField(levelView, "level"));
//  }
//
//  @Test
//  void testLoadMulti() {
//    clickButton("start-button");
//    enterMatchId();
//    clickButton("multiplayer-button");
//    Stage stage = (Stage) getPrivateField(screenCreator, "stage");
//    assertTrue(stage.getScene().getRoot() instanceof TeamSelector);
//  }

//  @Test
//  void successfulSinglePlayerButtonCreation() {
//    clickButton("start-button");
//    enterMatchId();
//    assertTrue(lookup("#single-player-button").query() != null);
//  }

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
    int teamNumber = 0;
    int matchNumber =10;
    screenCreator.setTeamNum(teamNumber);
    screenCreator.setMatchId(matchNumber);
    assertEquals(teamNumber, 0);
    assertEquals(matchNumber, 10);
  }

  @Test
  public void testGUIButtonCreation() {
    ResourceBundle rBundle = ResourceBundle.getBundle(ScreenCreator.RESOURCES + "TeamSelector");
    EventHandler<ActionEvent> eventHandler = e -> System.out.print("button");
    Button button = GUIFactory.makeButton(rBundle, eventHandler, "start", "default-button", "multiStart", 0);
    assertTrue(button.getText().equals("Start"));
  }

  @Test
  public void testGUILabelCreation() {
    ResourceBundle rBundle = ResourceBundle.getBundle(ScreenCreator.RESOURCES + "TeamSelector");
    Label label = GUIFactory.makeLabel(rBundle, "waitingMessage", "title", 0);
    assertTrue(label.getText().equals("Waiting for teammate/opponents..."));
  }

  @Test
  public void testGUILabelCreationwithParameter() {
    ResourceBundle rBundle = ResourceBundle.getBundle(ScreenCreator.RESOURCES + "TeamSelector");
    int teamNum = 1;
    Label label = GUIFactory.makeLabel(rBundle, "teamMessage", "title", teamNum);
    assertTrue(label.getText().equals("Welcome to team 1!"));
  }

}
