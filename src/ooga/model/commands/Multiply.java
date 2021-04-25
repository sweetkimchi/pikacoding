package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class Multiply extends MathematicalCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Multiply(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * The formula for calculating the new display number of the dataCube held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarCubeNum The current number of the cube held by the avatar
   * @param tileCubeNum The current number of the cube on the tile
   * @return The new number to be set to the avatar cube
   */
  @Override
  public int calculateNewDisplayNum(int avatarCubeNum, int tileCubeNum) {
    return avatarCubeNum * tileCubeNum;
  }
}