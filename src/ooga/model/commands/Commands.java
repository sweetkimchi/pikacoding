package ooga.model.commands;

import java.util.*;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public abstract class Commands implements CommandInterface {

  private ElementInformationBundle elementInformationBundle;
  private Map<String, String> parameters;

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Commands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    this.elementInformationBundle = elementInformationBundle;
    this.parameters = parameters;
  }

  /**
   * The execution behavior of the command on an Avatar given by an ID. The specific implementation
   * is to be overridden by the subclasses.
   *
   * @param ID The ID of the avatar to be commanded
   */
  public abstract void execute(int ID);

  /**
   * Generates a Direction object from the input string given by parameters of the command.
   *
   * @param direction The direction string
   * @return The Direction object corresponding to the input string
   */
  protected Direction getDirection(String direction) {
    return Direction.valueOf(parseDirection(direction));
  }

  private String parseDirection(String direction) {
    String parsedDirection = direction.toUpperCase();
    parsedDirection = parsedDirection.replaceAll("-", "_");
    return parsedDirection;
  }

  public ElementInformationBundle getElementInformationBundle() {
    return elementInformationBundle;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public Avatar getAvatar(int id) {
    return (Avatar) getElementInformationBundle().getAvatarById(id);
  }
}