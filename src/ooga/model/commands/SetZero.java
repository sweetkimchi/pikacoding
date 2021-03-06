package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

/**
 * SetZero is a type of Set DataCube Command that sets the value to the datacube held by the Avatar
 * to be 0.
 *
 * @author Harrison Huang
 */
public class SetZero extends SetDataCubeCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public SetZero(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * The formula for calculating the new display number of the dataCube held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarBlockNum The current number of the cube held by the avatar
   * @return The new number to be set to the avatar cube
   */
  @Override
  public int calculateNewDisplayNum(int avatarBlockNum) {
    return 0;
  }
}
