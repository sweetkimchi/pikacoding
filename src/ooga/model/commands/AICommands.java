package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

/**
 * @author Ji Yun Hyo
 */
public abstract class AICommands extends BasicCommands{

  public AICommands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }
}
