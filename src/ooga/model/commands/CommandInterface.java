package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * This is the command interface that defines the characteristics of all commands as designed by this game
 * @author Ji Yun Hyo
 */
public interface CommandInterface {

  /**
   * Executes the command on the given element
   * @param ID ID of the element
   */
  void execute(int ID);

  /**
   * Retrives the information bundle containing information about blocks and avatars
   * @return ElementInformation object that has get methods to information about the elements
   */
  ElementInformationBundle getElementInformationBundle();

  /**
   * Returns the parameters sent by the user as part of the command
   * @return parameter associated with a command
   */
  Map<String, String> getParameters();

  /**
   * Returns an Avatar queried by the ID number.
   *
   * @param id The ID number of the desired Avatar
   * @return The Avatar that has the given ID number
   */
  Avatar getAvatar(int id);

}
