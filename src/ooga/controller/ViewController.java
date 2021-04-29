package ooga.controller;

import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.view.ScreenCreator;
import ooga.view.level.LevelView;
import ooga.view.level.MultiplayerLevelView;
import ooga.view.level.codearea.CommandBlock;

/**
 * Facilitates interaction between model and view.
 * <p>
 * Specifically, responsible for receiving game state updates from the model and passing them to the
 * view.
 *
 * @author Ji Yun Hyo
 * @author David Li
 * @author Kathleen Chen
 */
public class ViewController implements FrontEndExternalAPI {

  BackEndExternalAPI modelController;
  ScreenCreator screenCreator;
  LevelView levelView;

  /**
   * Default constructor
   *
   * @param stage JavaFX stage of the application
   */
  public ViewController(Stage stage) {
    screenCreator = new ScreenCreator(this, stage);
  }

  /**
   * Sets the model controller to set up the line of communication from/to the backend
   *
   * @param modelController BackEndExternalAPI
   */
  @Override
  public void setModelController(BackEndExternalAPI modelController) {
    this.modelController = modelController;
  }

  /**
   * Calls the view to open the start menu of the game
   */
  @Override
  public void loadStartMenu() {
    screenCreator.loadStartMenu();
  }

  /**
   * Loads the level view in the front-end and calls the model to initialize and pass the level info
   * to the view.
   *
   * @param level Level number of the initialized level
   */
  @Override
  public void initializeMultiLevel(int level) {
    screenCreator.initializeMultiLevelView(level);
    levelView = screenCreator.getLevelView();
    modelController.initializeLevel(level);
  }

  /**
   * Loads the level view in the front-end and calls the model to initialize and pass the level info
   * to the view.
   *
   * @param level Level number of the initialized level
   */
  @Override
  public void initializeSingleLevel(int level) {
    screenCreator.initializeSingleLevelView(level);
    levelView = screenCreator.getLevelView();
    modelController.initializeLevel(level);
    levelView.resetAnimation();
  }

  /**
   * Sets the view board to contain a new level. Instantiates all the elements of the grid,
   * including the dimensions and initial locations of avatars and blocks.
   *
   * @param gameGridData The tile information of the grid
   * @param initialState The initial state of the board sprites
   */
  @Override
  public void setBoard(GameGridData gameGridData, InitialState initialState) {
    levelView.initializeBoard(gameGridData, initialState);
  }

  /**
   * Sets the available commands with their parameters and parameter options
   *
   * @param availableCommands Map from command names to a list of parameters that map to a list of
   *                          the parameter options.
   */
  @Override
  public void setAvailableCommands(AvailableCommands availableCommands) {
    levelView.setAvailableCommands(availableCommands);
  }

  @Override
  public void setAvailableCommandsOtherPlayer(AvailableCommands availableCommands) {
    ((MultiplayerLevelView) levelView).setAvailableCommandsOtherPlayer(availableCommands);
  }

  /**
   * Sets the number of starting apples for the level
   * @param apples Number of apples
   */
  @Override
  public void setStartingApples(int apples) {
    levelView.setStartingApples(apples);
  }

  /**
   * Sets the description/goal of the level
   * @param description Description of the level
   */
  @Override
  public void setDescription(String description) {
    levelView.setDescription(description);
  }

  /**
   * Passes in the commands to be parsed
   *
   * @param commandBlocks List of individual command blocks derived from the blocks in the
   */
  @Override
  public void parseCommands(List<CommandBlock> commandBlocks) {
    modelController.parseCommands(commandBlocks);
  }

  /**
   * Runs the next command in the command queue
   */
  @Override
  public void runNextCommand() {
    modelController.runNextCommand();
  }

  /**
   * Updates a specific avatar's position to new a new location on the grid
   *
   * @param id ID of the avatar
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  @Override
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    levelView.updateAvatarPosition(id, xCoord, yCoord);
  }

  /**
   * Updates a specific block's position to new a new location on the grid
   *
   * @param id ID of the block
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  @Override
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    levelView.updateBlockPosition(id, xCoord, yCoord);
  }

  /**
   * Update the status of the block
   * True = block is being held by an avatar
   * False = block is not being held by an avatar
   * @param id ID of the block
   * @param b boolean indicating whether the block is being held or not
   */
  @Override
  public void updateBlock(int id, boolean b) {
    levelView.updateBlock(id, b);
  }

  /**
   * Sets the number on the block to a new number
   * @param id ID of the block
   * @param newDisplayNum new number to be displayed on the block
   */
  @Override
  public void setBlockNumber(int id, int newDisplayNum) {
    levelView.setBlockNumber(id, newDisplayNum);
  }

  /**
   * All commands have reached the end and no more to be executed
   */
  @Override
  public void declareEndOfRun() {
    levelView.declareEndOfRun();
  }

  /**
   * Sets the line numbers for the avatars
   *
   * @param lineUpdates Map from id of avatar to line number
   */
  @Override
  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    levelView.setLineIndicators(lineUpdates);
  }

  /**
   * Sets the score that the player has
   * @param score Player score
   */
  @Override
  public void setScore(int score) {
    levelView.setScore(score);
  }

  /**
   * Notifies the view that the player has won
   */
  @Override
  public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    levelView.winLevel(executionScore, bonusFromNumberOfCommands, bonusFromTimeTaken);
  }

  /**
   * Notifies the view that the player has lost
   */
  @Override
  public void loseLevel() {
    levelView.loseLevel();
  }

  /**
   * Updates the program (model) in the backend by providing it with a new set of
   * CommandBlock objects to parse and execute
   * @param program list of CommandBlock objects containing information about each command block
   */
  @Override
  public void sendProgramUpdates(List<CommandBlock> program) {
    modelController.updateProgram(program);
  }

  /**
   * Relays update program information
   * @param program list of command blocks
   */
  @Override
  public void receiveProgramUpdates(List<CommandBlock> program) {
    levelView.receiveProgramUpdates(program);
  }

  /**
   * Checks whether there is time left or not in the game. This method is called by the frontend
   * during the animation to see if they should proceed
   */
  @Override
  public void checkTimeLeftOrNot() {
    modelController.checkTimeLeftOrNot();
  }

  /**
   * This method is called when the game has timed out.
   */
  @Override
  public void timedOut() {
    levelView.timedOut();
  }

  /**
   * Updates the time left. This method is part of the animation for show how much time
   * the player has left on the GUI
   * @param timeLeft seconds representing the amount of time the player has left
   */
  @Override
  public void updateTime(int timeLeft) {
    levelView.updateTime(timeLeft);
  }

  /**
   * sets the team number selected by the player from the view
   */
  @Override
  public void setTeamNum(int teamNum) { modelController.setTeamNumber(teamNum); }

  /**
   * Set match ID for the current game.
   * Assumed to be non negative, and match ID doesn't have a corresponding match in firebase
   * @param id id of match
   */
  @Override
  public void setMatchId(int id) { modelController.setMatchId(id); }

  /**
   * Method that is called when all four players are present. Two players on each team.
   */
  @Override
  public void notifyBothTeamsPresent() {
    screenCreator.setTeamReady();
  }

  /**
   * Method that is called when the team the player is currently playing on has won.
   * Either they have won, or their teammate has won.
   *
   * @param score of the winning player
   */
  @Override
  public void notifyCurrentTeamFinished(int score) {
    levelView.notifyCurrentTeamFinished(score);
  }

  /**
   * Method that is called when both teams in the current game have successfully finished the level.
   *
   * @param currentScore score of the current player's team
   * @param otherScore score of the opposing team
   */
  @Override
  public void notifyBothTeamsFinished(int currentScore, int otherScore) {
    levelView.notifyBothTeamsFinished(currentScore, otherScore);
  }

}