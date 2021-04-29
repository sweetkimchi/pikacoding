

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
   * initializes the level
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

  void updateAvatarPosition(int id, int xCoord, int yCoord);

  void updateBlockPosition(int id, int xCoord, int yCoord);

  void updateBlock(int id, boolean b);

  void setBlockNumber(int id, int newDisplayNum);

  /**
   * All commands have reached the end and no more to be executed
   */
  void declareEndOfRun();

  /**
   * updates the line numbers for the avatars
   *
   * @param lineUpdates
   */
  void setLineIndicators(Map<Integer, Integer> lineUpdates);

  /**
   * updates the score and sends it to the frontend
   *
   * @param score
   */
  void setScore(int score);

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