package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class Decrement extends SetDataCubeCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Decrement(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public int calculateNewDisplayNum(int avatarCubeNum) {
    return avatarCubeNum - 1;
  }
}
