package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * The Tell command takes in one parameter, the desired ID of the avatar, and directs only that
 * avatar to run the code within the Tell brackets. It is intended to be paired with an end tell
 * command, which demarcates when the Tell ends.
 *
 * @author Harrison Huang
 */
public class Tell extends ConditionalCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Tell(ElementInformationBundle elementInformationBundle,
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
    int idToCheck = Integer.parseInt(getParameters().get("id"));
    if (ID == idToCheck) {
      avatar.setProgramCounter(avatar.getProgramCounter() + 1);
    }
    else {
      // TODO: find next line after end tell and set PC to that
      avatar.setProgramCounter(getElementInformationBundle().getMapOfCommandLines().get(avatar.getProgramCounter()));
    }
  }
}
