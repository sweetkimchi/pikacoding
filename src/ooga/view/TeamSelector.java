package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Consumer;


public class TeamSelector extends BorderPane {
  private static final String TEAM_SELECTOR_STRINGS = ScreenCreator.RESOURCES + "TeamSelector";

  private ResourceBundle teamSelectorResources;
  private Consumer<Integer> levelAction;
  private ScreenCreator screenCreator;

  public TeamSelector(Consumer<Integer> lAction, ScreenCreator sc) {
    screenCreator = sc;
    levelAction = lAction;
    teamSelectorResources = ResourceBundle.getBundle(TEAM_SELECTOR_STRINGS);
    createTeamChoices();
  }

  private void createTeamChoices() {
    HBox teamButtons = new HBox();
    teamButtons.getStyleClass().add("type-screen");

    Button team1 = new Button(teamSelectorResources.getString("team1"));
    team1.setId(ScreenCreator.idsForTests.getString("team1"));
    team1.getStyleClass().add("default-button");
    team1.setOnAction(e -> determineTeam(1));
    Button team2 = new Button(teamSelectorResources.getString("team2"));
    team2.setId(ScreenCreator.idsForTests.getString("team2"));
    team2.getStyleClass().add("default-button");
    team2.setOnAction(e -> determineTeam(2));
    teamButtons.getChildren().addAll(team1, team2);

    VBox vBox = new VBox();
    vBox.getStyleClass().add("instruction-box");

    Label instructions = new Label(teamSelectorResources.getString("instructions"));
    instructions.getStyleClass().add("title");

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
    Label teamMessage = new Label(applyResourceFormatting(teamNumber, teamSelectorResources.getString("teamMessage")));
    teamMessage.getStyleClass().add("team-message");
    Label waitingMessage = new Label(teamSelectorResources.getString("waitingMessage"));
    tBox.getChildren().addAll(teamMessage, waitingMessage);
    tBox.getStyleClass().add("instruction-box");
    this.setCenter(tBox);
  }

  //TODO: I use this a lot so refactor in some factory or inheritance hiearchy
  private String applyResourceFormatting(int num, String key) {
    Object[] var = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    var[0] = num;
    formatter.applyPattern(key);
    return formatter.format(var);
  }
}
