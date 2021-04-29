package ooga.controller;

import java.util.List;
import java.util.Map;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.view.level.codearea.CommandBlock;

/**
 * Facilitates interaction between model and view.
 * <p>
 * Specifically, responsible for receiving game state updates from the model and passing them to the
 * view.
 *
 * @author David Li
 */
public interface FrontEndExternalAPI {

  /**
   * Sets the model controller to set up the line of communication from/to the backend
   *
   * @param modelController BackEndExternalAPI
   */
  void setModelController(BackEndExternalAPI modelController);

  /**
   * Calls the view to open the start menu of the game
   */
  void loadStartMenu();

  /**
   * Loads the multiplayer level view in the front-end and calls the model to initialize and pass the level info
   * to the view.
   *
   * @param level Level number of the initialized level
   */
  void initializeMultiLevel(int level);

  /**
   * Loads the single palyerlevel view in the front-end and calls the model to initialize and pass the level info
   * to the view.
   *
   * @param level Level number of the initialized level
   */
  void initializeSingleLevel(int level);

  /**
   * Sets the view board to contain a new level. Instantiates all the elements of the grid,
   * including the dimensions and initial locations of avatars and blocks.
   *
   * @param gameGridData The tile information of the grid
   * @param initialState The initial state of the board sprites
   */
  void setBoard(GameGridData gameGridData, InitialState initialState);

  /**
   * Sets the available commands with their parameters and parameter options
   *
   * @param availableCommands Map from command names to a list of parameters that map to a list of
   *                          the parameter options.
   */
  void setAvailableCommands(AvailableCommands availableCommands);

  /**
   * Sets the available commands of the other player with their parameters and parameter options
   *
   * @param availableCommands Map from command names to a list of parameters that map to a list of
   *                          the parameter options.
   */
  void setAvailableCommandsOtherPlayer(AvailableCommands availableCommands);

  /**
   * Sets the number of starting apples for the level
   * @param apples Number of apples
   */
  void setStartingApples(int apples);

  /**
   * Sets the description/goal of the level
   * @param description Description of the level
   */
  void setDescription(String description);

  /**
   * Passes in the commands to be parsed
   *
   * @param commandBlocks List of individual command blocks derived from the blocks in the
   *                      CodeBuilderArea
   */
  void parseCommands(List<CommandBlock> commandBlocks);

  /**
   * Runs the next command in the command queue
   */
  void runNextCommand();

  /**
   * Update the position of a single avatar
   * @param id Id of the avatar
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  void updateAvatarPosition(int id, int xCoord, int yCoord);

  /**
   * Update the position of a single block
   * @param id Id of the block
   * @param xCoord New x-coordinate
   * @param yCoord New y-coordinate
   */
  void updateBlockPosition(int id, int xCoord, int yCoord);

  /**
   * Updates the held state of a block
   * @param id Id of the block
   * @param b Whether the block is held or not
   */
  void updateBlock(int id, boolean b);

  /**
   * Sets the number displayed on a block
   * @param id Id of the block
   * @param newDisplayNum New number to be displayed
   */
  void setBlockNumber(int id, int newDisplayNum);

  void declareEndOfRun();

  /**
   * Sets the line numbers for the avatars
   *
   * @param lineUpdates Map from id of avatar to line number
   */
  void setLineIndicators(Map<Integer, Integer> lineUpdates);

  /**
   * Sets the score that the player has
   * @param score Player score
   */
  void setScore(int score);

  /**
   * Clears the screen and displays the win screen
   * @param executionScore Score from number of lines executed
   * @param bonusFromNumberOfCommands Bonus from number of commands used
   * @param bonusFromTimeTaken Bonus from amount of time used
   */
  void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken);

  /**
   * Notifies the view that the player has lost
   */
  void loseLevel();

  /**
   * Sends local updates of the program to the database
   * @param program The updated program
   */
  void sendProgramUpdates(List<CommandBlock> program);

  /**
   * Updates the local program to sync with the database program
   * @param program New program stack
   */
  void receiveProgramUpdates(List<CommandBlock> program);

  void checkTimeLeftOrNot();

  void timedOut();

  /**
   * Updates the front-end with the amount of time left
   * @param timeLeft Amount of time left
   */
  void updateTime(int timeLeft);

  /**
   * Sets the team number of the player
   * @param teamNum The team number
   */
  void setTeamNum(int teamNum);

  /**
   * Sets the match id
   * @param id The match id
   */
  void setMatchId(int id);

  /**
   * Notifies the view that both teams are present and the game is ready to start
   */
  void notifyBothTeamsPresent();

  /**
   * Notifies the front-end that the current team has beaten the level
   * @param score Score that the team achieved
   */
  void notifyCurrentTeamFinished(int score);

  /**
   * Notifies the front-end that both teams have beaten the level
   * @param team1Score Score that the current team achieved
   * @param team2Score Score that the other team achieved
   */
  void notifyBothTeamsFinished(int team1Score, int team2Score);
}
