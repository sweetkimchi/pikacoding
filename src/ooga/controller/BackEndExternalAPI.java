

package ooga.controller;

import java.util.List;
import java.util.Map;
import ooga.view.level.codearea.CommandBlock;

/**
 * Facilitates interaction between view and model.
 *
 * @author Ji Yun Hyo Specifically, responsible for receiving unparsed commands and passing them to
 * the model to be parsed and ran.
 * @author billyluqiu responsible for database updates.
 */
public interface BackEndExternalAPI {

  /**
   * sets the view controller to set up the line of communication from/to the backend
   *
   * @param viewController FrontEndExternalAPI
   */
  void setViewController(FrontEndExternalAPI viewController);

  /**
   * Initializes the level
   *
   * @param level integer indicating the level
   * @return BoardState object with level information
   */
  void initializeLevel(int level);

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
   * Updates the line numbers for the avatars
   *
   * @param lineUpdates
   */
  void setLineIndicators(Map<Integer, Integer> lineUpdates);

  /**
   * Updates the score and sends it to the frontend
   *
   * @param score
   */
  void setScore(int score);

  /**
   * Wins the level for the player. This method is the method that is called when the
   * goal state is met. It returns 3 sets of scores calculated according to the
   * criteria
   * @param executionScore score corresponding to how many
   * @param bonusFromNumberOfCommands
   * @param bonusFromTimeTaken
   */
  void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken);

  void loseLevel();

  void updateProgram(List<CommandBlock> program);

  void checkTimeLeftOrNot();

  void timedOut();

  void updateTime(int timeLeft);

  void receivedProgramUpdate(List<CommandBlock> program);

  /**
   * sets the team number selected by the player from the view
   */
  void setTeamNumber(int teamNum);

  /**
   * Method that is called when all four players are present. Two players on each team.
   */
  void startGameAfterBothTeamsPresent();

  /**
   * Set match ID for the current game.
   * Assumed to be non negative, and match ID doesn't have a corresponding match in firebase
   * @param id id of match
   */
  void setMatchId(int id);

  /**
   * Method that is called when the team the player is currently playing on has won.
   * Either they have won, or their teammate has won.
   *
   * @param score of the winning player
   */
  void notifyCurrentTeamEnded(int score);

  /**
   * Method that is called when both teams in the current game have succesfully finished the level.
   *
   * @param team1_score score of the current player's team
   * @param team2_score score of the opposing team
   */
  void notifyBothTeamsEnded(int team1_score, int team2_score);
}