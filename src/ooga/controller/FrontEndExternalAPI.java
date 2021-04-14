package ooga.controller;

import java.util.List;
import java.util.Map;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.BoardState;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.AvatarData;
import ooga.model.player.Element;
import ooga.view.level.codearea.CommandBlock;

/**
 * Facilitates interaction between model and view.
 *
 * Specifically, responsible for receiving game state updates from the model and passing them to the view.
 */
public interface FrontEndExternalAPI {

  /**
   * sets the model controller to set up the line of communication from/to the backend
   * @param modelController BackEndExternalAPI
   */
  public void setModelController(BackEndExternalAPI modelController);

  /**
   * Sets the view board to contain a new level. Instantiates all the elements of the grid, including the dimensions and initial locations of humans and objects.
   *
   * @param gameGridData The tile information of the grid
   * @param initialState The initial state of the board sprites
   */
  public void setBoard(GameGridData gameGridData, InitialState initialState);

  /**
   * Sets the available commands with their parameters and parameter options
   * @param availableCommands Map from command names to a list of parameters that map to a list of the parameter options.
   */
  public void setAvailableCommands(AvailableCommands availableCommands);

  /**
   * Updates and individual sprite (avatars, block)
   * @param id Id of the sprite to be updated
   * @param spriteData Representation of element of the game
   */
  public void updateSprite(int id, Element spriteData);

  /**
   * Passes in the commands to be parsed
   * @param commandBlocks List of individual command blocks derived from the blocks in the CodeBuilderArea
   */
  public void parseCommands(List<CommandBlock> commandBlocks);

  /**
   * Runs the next command in the command queue
   */
  public void runNextCommand();

  /**
   * Sets the position of the sprite
   */
  public void setPosition(double x, double y, int id);

  void setActiveAvatar(int avatarID);

  void initializeLevel(int level);

  void updateAvatarPositions(int id, int xCoord, int yCoord);

  void updateFrontEndElements(Map<String, AvatarData> updates);

  void declareEndOfAnimation();

  void setScore(int score);

  /**
   * sets the line number for the avatar
   * @param lineUpdates
   */
  void setLineIndicators(Map<Integer, Integer> lineUpdates);
}
