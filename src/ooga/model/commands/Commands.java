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
    Direction dummy = Direction.SELF;
    if(direction.equals("up")){
      dummy = Direction.UP;
    }
    if(direction.equals("up-right")){
      dummy = Direction.UP_RIGHT;
    }
    if(direction.equals("right")){
      dummy = Direction.RIGHT;
    }
    if(direction.equals("down-right")){
      dummy = Direction.DOWN_RIGHT;
    }
    if(direction.equals("down")){
      dummy = Direction.DOWN;
    }
    if(direction.equals("down-left")){
      dummy = Direction.DOWN_LEFT;
    }
    if(direction.equals("left")){
      dummy = Direction.LEFT;
    }
    if(direction.equals("up-left")){
      dummy = Direction.UP_LEFT;
    }
    return dummy;
  }

  public ElementInformationBundle getElementInformationBundle() {
    return elementInformationBundle;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}