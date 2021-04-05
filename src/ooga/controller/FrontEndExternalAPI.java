package ooga.controller;

import java.util.List;
import ooga.model.grid.gridData.BoardState;
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
   * @param boardState The initial state of the board
   */
  public void setBoard(BoardState boardState);

  /**
   * Updates and individual sprite (avatars, block)
   * @param id Id of the sprite to be updated
   * @param spriteData Representation of element of the game
   */
  public void updateSprite(int id, Element spriteData);

  /**
   * Passes in the commands to be parsed and executed
   * @param commandBlocks List of individual command blocks derived from the blocks in the CodeBuilderArea
   */
  public void parseAndExecuteCommands(List<CommandBlock> commandBlocks);

  /**
   * Sets the position of the sprite
   */
  public void setPosition(double x, double y, int id);

  void setActiveAvatar(int avatarID);

}
