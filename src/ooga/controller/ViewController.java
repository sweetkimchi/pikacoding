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
   * Update the position of a single avatar
   * @param id Id of the avatar
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  @Override
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    levelView.updateAvatarPosition(id, xCoord, yCoord);
  }

  /**
   * Update the position of a single block
   * @param id Id of the block
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  @Override
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    levelView.updateBlockPosition(id, xCoord, yCoord);
  }

  /**
   * Updates the held state of a block
   * @param id Id of the block
   * @param b Whether the block is held or not
   */
  @Override
  public void updateBlock(int id, boolean b) {
    levelView.updateBlock(id, b);
  }

  /**
   * Sets the number displayed on a block
   * @param id Id of the block
   * @param newDisplayNum New number to be displayed
   */
  @Override
  public void setBlockNumber(int id, int newDisplayNum) {
    levelView.setBlockNumber(id, newDisplayNum);
  }

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
   * Clears the screen and displays the win screen
   * @param executionScore Score from number of lines executed
   * @param bonusFromNumberOfCommands Bonus from number of commands used
   * @param bonusFromTimeTaken Bonus from amount of time used
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
   * Sends local updates of the program to the database
   * @param program The updated program
   */
  @Override
  public void sendProgramUpdates(List<CommandBlock> program) {
    modelController.updateProgram(program);
  }

  /**
   * Updates the local program to sync with the database program
   * @param program New program stack
   */
  @Override
  public void receiveProgramUpdates(List<CommandBlock> program) {
    levelView.receiveProgramUpdates(program);
  }

  @Override
  public void checkTimeLeftOrNot() {
    modelController.checkTimeLeftOrNot();
  }

  @Override
  public void timedOut() {
    levelView.timedOut();
  }

  /**
   * Updates the front-end with the amount of time left
   * @param timeLeft Amount of time left
   */
  @Override
  public void updateTime(int timeLeft) {
    levelView.updateTime(timeLeft);
  }

  /**
   * Sets the team number of the player
   * @param teamNum The team number
   */
  @Override
  public void setTeamNum(int teamNum) { modelController.setTeamNumber(teamNum); }

  /**
   * Sets the match id
   * @param id The match id
   */
  @Override
  public void setMatchId(int id) { modelController.setMatchId(id); }

  /**
   * Notifies the view that both teams are present and the game is ready to start
   */
  @Override
  public void notifyBothTeamsPresent() {
    screenCreator.setTeamReady();
  }

  /**
   * Notifies the front-end that the current team has beaten the level
   * @param score Score that the team achieved
   */
  @Override
  public void notifyCurrentTeamFinished(int score) {
    levelView.notifyCurrentTeamFinished(score);
  }

  /**
   * Notifies the front-end that both teams have beaten the level
   * @param currentScore Score that the current team achieved
   * @param otherScore Score that the other team achieved
   */
  @Override
  public void notifyBothTeamsFinished(int currentScore, int otherScore) {
    levelView.notifyBothTeamsFinished(currentScore, otherScore);
  }

}