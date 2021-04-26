package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

/**
 * Add is a type of Mathematical Command that takes the sum of the display number of two blocks, one
 * held by the Avatar and the other on the tile that the Avatar is standing on, and sets this value
 * to the block held by the Avatar.
 *
 * @author Harrison Huang
 */
public class Add extends MathematicalCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Add(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * The formula for calculating the new display number of the block held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarCubeNum The current number of the block held by the avatar
   * @param tileCubeNum   The current number of the block on the tile
   * @return The new number to be set to the avatar block
   */
  @Override
  public int calculateNewDisplayNum(int avatarCubeNum, int tileCubeNum) {
    return avatarCubeNum + tileCubeNum;
  }
}