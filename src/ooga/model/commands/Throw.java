package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class Throw extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Throw(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {

  }
}
