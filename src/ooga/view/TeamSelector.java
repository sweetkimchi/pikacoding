package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

import java.util.Random;
import java.util.function.Consumer;


public class TeamSelector extends BorderPane {

  private int teamNumber;
  private Consumer<Integer> levelAction;

  public TeamSelector(Consumer<Integer> lAction) {
    levelAction = lAction;
    createTeamChoices();
  }

  private void createTeamChoices() {
    HBox teamButtons = new HBox();
    teamButtons.getStyleClass().add("type-screen");

    Button team1 = new Button("Team 1");
    team1.getStyleClass().add("default-button");
    team1.setOnAction(e -> determineTeam(1));
    Button team2 = new Button("Team 2");
    team2.getStyleClass().add("default-button");
    team2.setOnAction(e -> determineTeam(2));
    teamButtons.getChildren().addAll(team1, team2);

    VBox vBox = new VBox();
    vBox.getStyleClass().add("instruction-box");

    Label instructions = new Label("Choose a team");
    instructions.getStyleClass().add("title");

    vBox.getChildren().addAll(instructions, teamButtons);
    this.setCenter(vBox);
  }

  private void determineTeam(int teamNum) {
    teamNumber = teamNum;
    this.getChildren().clear();
    createTeamScreen();
  }

  private void createTeamScreen() {
    VBox tBox = new VBox();
    Label teamMessage = new Label("Welcome to team " + teamNumber + "!");
    teamMessage.getStyleClass().add("team-message");
    Label waitingMessage = new Label("Waiting for teammate/opponents...");
    tBox.getChildren().addAll(teamMessage, waitingMessage);
    tBox.getStyleClass().add("instruction-box");

    // TODO: REMOVE LATER WHEN YOU FIGURE OUT HOW TO SINK THE LEVELS
    Button start = new Button("Start");
    tBox.getChildren().add(start);
    start.setOnAction(handler -> {
      tBox.getChildren().remove(waitingMessage);
      Random r = new Random();
      int level = r.nextInt(Controller.NUM_LEVELS) + 1;
      levelAction.accept(level);
    });
    this.setCenter(tBox);
  }

  public int getTeamNumber() {
    return teamNumber;
  }
}
