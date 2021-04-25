

package ooga.controller;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.Map;
import ooga.model.player.AvatarData;
import ooga.view.level.codearea.CommandBlock;

/**
 * Facilitates interaction between view and model.
 *
 * @author Ji Yun Hyo Specifically, responsible for receiving unparsed commands and passing them to
 * the model to be parsed and ran.
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
}