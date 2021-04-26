package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

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

  @Override
  public int calculateNewDisplayNum(int avatarCubeNum) {
    return 0;
  }
}
