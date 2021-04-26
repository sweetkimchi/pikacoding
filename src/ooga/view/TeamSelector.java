package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;

import java.util.ResourceBundle;
import java.util.function.Consumer;


public class TeamSelector extends BorderPane {
  private static final String TEAM_SELECTOR_STRINGS = ScreenCreator.RESOURCES + "TeamSelector";
  private static final int NO_NUM = 0;

  private ResourceBundle teamSelectorResources;
  private ScreenCreator screenCreator;
  private GUIElementInterface GUIFactory;
  private int numberOfTeams;
  private EventHandler<ActionEvent> loadLevelSelector;

  public TeamSelector(EventHandler<ActionEvent> loadAction, ScreenCreator sc) {
    GUIFactory = new GUIElementFactory();
    screenCreator = sc;
    loadLevelSelector = loadAction;
    teamSelectorResources = ResourceBundle.getBundle(TEAM_SELECTOR_STRINGS);
    numberOfTeams = Integer.parseInt(teamSelectorResources.getString("numTeams"));
    createTeamChoices();
  }

  private void createTeamChoices() {
    HBox teamButtons = new HBox();
    teamButtons.getStyleClass().add("type-screen");

    for (int i = 1; i <= numberOfTeams; i ++) {
      int teamNum = i;
      Button team = GUIFactory.makeButton(teamSelectorResources, e -> determineTeam(teamNum), "team" + teamNum, "default-button", "team" + teamNum, NO_NUM);
      teamButtons.getChildren().add(team);
    }

    VBox vBox = new VBox();
    vBox.getStyleClass().add("instruction-box");
    
    Label instructions = GUIFactory.makeLabel(teamSelectorResources, "instructions", "title", NO_NUM);

    vBox.getChildren().addAll(instructions, teamButtons);
    this.setCenter(vBox);
  }

  private void determineTeam(int teamNum) {
    screenCreator.setTeamNum(teamNum);
    this.getChildren().clear();
    createTeamScreen(teamNum);
  }

  private void createTeamScreen(int teamNumber) {
    VBox tBox = new VBox();

    Label teamMessage = GUIFactory.makeLabel(teamSelectorResources, "teamMessage", "team-message", teamNumber);
    Label waitingMessage = GUIFactory.makeLabel(teamSelectorResources, "waitingMessage", "waiting-message", NO_NUM);

    tBox.getChildren().addAll(teamMessage, waitingMessage);
    tBox.getStyleClass().add("instruction-box");
    this.setCenter(tBox);

    Button start = GUIFactory.makeButton(teamSelectorResources, loadLevelSelector, "start", "default-button", "multiStart", NO_NUM);
    tBox.getChildren().add(start);
    this.setCenter(tBox);
  }
}
