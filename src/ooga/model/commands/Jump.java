package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * Jump is a command that directly changes the program counter of the given avatar. The avatar will
 * set its program counter to be that specified by "destination" in the input parameter map.
 *
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public class Jump extends ControlFlowCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Jump(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * The execution behavior of the command on an Avatar given by an ID. The specific implementation
   * is to be overridden by the subclasses.
   *
   * @param ID The ID of the avatar to be commanded
   */
  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    avatar.setProgramCounter(Integer.parseInt(getParameters().get("destination")));
  }
}