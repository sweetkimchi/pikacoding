package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * The Endif command demarcates the end of the commands to be run only when the if statement is
 * satisfied. Endif is intended to be paired with a corresponding If command. If the corresponding
 * If is not satisfied, then the avatar will set its program counter to the same as the present
 * Endif, which ends the if loop.
 *
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public class Endif extends ConditionalCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Endif(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
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
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }
}
