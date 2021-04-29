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
   * Updates a specific avatar's position to new a new location on the grid
   *
   * @param id ID of the avatar
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  void updateAvatarPosition(int id, int xCoord, int yCoord);

  /**
   * Updates a specific block's position to new a new location on the grid
   *
   * @param id ID of the block
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  void updateBlockPosition(int id, int xCoord, int yCoord);

  /**
   * Update the status of the block
   * True = block is being held by an avatar
   * False = block is not being held by an avatar
   * @param id ID of the block
   * @param b boolean indicating whether the block is being held or not
   */
  void updateBlock(int id, boolean b);

  /**
   * Sets the number on the block to a new number
   * @param id ID of the block
   * @param newDisplayNum new number to be displayed on the block
   */
  void setBlockNumber(int id, int newDisplayNum);

  /**
   * All commands have reached the end and no more to be executed
   */
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
   * Notifies the view that the player has won
   */
  void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken);

  /**
   * Informs the frontend the player has lost the game. This method gets called whenever
   * the model determines that the game has been lost.
   */
  void loseLevel();

  /**
   * Updates the program (model) in the backend by providing it with a new set of
   * CommandBlock objects to parse and execute
   * @param program list of CommandBlock ojbects containing information about each command block
   */
  void sendProgramUpdates(List<CommandBlock> program);

  /**
   * Relays update program information
   * @param program list of commnd blocks
   */
  void receiveProgramUpdates(List<CommandBlock> program);

  /**
   * Checks whether there is time left or not in the game. This method is called by the frontend
   * during the animation to see if they should proceed
   */
  void checkTimeLeftOrNot();

  /**
   * This method is called when the game has timed out.
   */
  void timedOut();

  /**
   * Updates the time left. This method is part of the animation for show how much time
   * the player has left on the GUI
   * @param timeLeft seconds representing the amount of time the player has left
   */
  void updateTime(int timeLeft);

  /**
   * sets the team number selected by the player from the view
   */
  void setTeamNum(int teamNum);

  /**
   * Set match ID for the current game.
   * Assumed to be non negative, and match ID doesn't have a corresponding match in firebase
   * @param id id of match
   */
  void setMatchId(int id);

  /**
   * Method that is called when all four players are present. Two players on each team.
   */
  void notifyBothTeamsPresent();

  /**
   * Method that is called when the team the player is currently playing on has won.
   * Either they have won, or their teammate has won.
   *
   * @param score of the winning player
   */
  void notifyCurrentTeamFinished(int score);

  /**
   * Method that is called when both teams in the current game have succesfully finished the level.
   *
   * @param team1_score score of the current player's team
   * @param team2_score score of the opposing team
   */
  void notifyBothTeamsFinished(int team1Score, int team2Score);
}
