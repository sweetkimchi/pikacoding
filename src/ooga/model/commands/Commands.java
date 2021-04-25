package ooga.model.commands;

import java.util.*;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public abstract class Commands implements CommandInterface{

  private ElementInformationBundle elementInformationBundle;
  private Map<String,String> parameters;
  public Commands(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    this.elementInformationBundle = elementInformationBundle;
    this.parameters = parameters;
  }

  public abstract void execute(int ID);

  Direction getDirection(String direction) {
    return Direction.valueOf(parseDirection(direction));
  }

  protected String parseDirection(String direction){
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