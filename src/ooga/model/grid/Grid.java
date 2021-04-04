package ooga.model.grid;

import java.util.List;
import ooga.model.commands.Commands;
import ooga.model.player.ElementData;
import ooga.model.player.Elements;

/**
 * Grid contains the field with all of the avatars and objects, specific to each new level. It
 * can have spaces containing different types of terrain, such as avatars, objects,
 * walls/holes/obstacles, etc.
 *
 * @author Harrison Huang
 * @author Ji Yun Hyo
 */
public interface Grid {

  /**
   * Sets the base dimensions of the grid.
   *
   * @param width The width of the grid
   * @param height The height of the grid
   */
  public void setDimensions(int width, int height);

  /**
   * Adds a game element to a specified location on the grid.
   *
   * @param gameElement A generic element to be placed on the grid
   */
  public void addGameElement(Elements gameElement, int xPos, int yPos);

//  /**
//   * Executes a command on all of the avatars.
//   *
//   * @param command A command to be executed on avatars
//   */
//  public void executeOnAvatars(Commands command);

  /**
   * Returns a list of sprites that have been changed in the grid.
   *
   * @return The list of changed sprites
   */
  public List<ElementData> getChangedSprites();

}
