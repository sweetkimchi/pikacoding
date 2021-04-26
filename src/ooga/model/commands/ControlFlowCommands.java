package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

/**
 * @author Ji Yun Hyo
 */
public abstract class ControlFlowCommands extends Commands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public ControlFlowCommands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

}