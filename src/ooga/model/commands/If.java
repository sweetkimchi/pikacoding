package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.Tile;
import ooga.model.grid.gridData.TileData;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class If extends ConditionalCommands {

  /**
   * Default constructor
   */
  public If(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    Direction direction = getDirection(getParameters().get("direction"));
    int newX = avatar.getXCoord() + direction.getXDel();
    int newY = avatar.getYCoord() + direction.getYDel();
    TileData tileToCheck = getElementInformationBundle().getTileData(newX, newY);

    // equals or not equals
    String condition = getParameters().get("condition");
    boolean equalsOrNot = !condition.equals("equal");

    // TODO: refactor using reflection
    boolean result;
    String thingToCheck = getParameters().get("object");
    switch (thingToCheck) {
      case "nothing" -> result = (!tileToCheck.hasAvatar() && !tileToCheck.hasBlock()
          && tileToCheck.getStructure() == Structure.FLOOR);
      case "datacube" -> result = tileToCheck.hasBlock();
      case "avatar" -> result = tileToCheck.hasAvatar();
      case "wall" -> result = tileToCheck.getStructure() == Structure.WALL;
      case "hole" -> result = tileToCheck.getStructure() == Structure.HOLE;
      default -> throw new IllegalStateException("Unexpected value: " + thingToCheck);
    }
    if (result ^ equalsOrNot) {
      avatar.setProgramCounter(avatar.getProgramCounter() + 1);
    }
    else {
      // TODO: find next line after end if and set PC to that
    }
  }
}