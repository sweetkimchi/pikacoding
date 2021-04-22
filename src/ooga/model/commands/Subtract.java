package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class Subtract extends MathematicalCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Subtract(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public int calculateNewDisplayNum(int avatarCubeNum, int tileCubeNum) {
    return avatarCubeNum - tileCubeNum;
  }
}