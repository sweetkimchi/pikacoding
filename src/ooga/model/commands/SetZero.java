package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class SetZero extends SetDataCubeCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
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
