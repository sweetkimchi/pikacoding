package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.TileData;
import ooga.model.player.Avatar;

/**
 * The If command checks if a conditional is satisfied for an avatar given input parameters of a
 * tile direction, comparator, and target. If is intended to be paired with an Endif, which marks
 * the end of the If code. If the conditional is satisfied, then the avatar will run the immediately
 * preceding code, but if the conditional is not satisfied, then the avatar will jump to the
 * corresponding end if, which demarcates the limits of If.
 *
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public class If extends ConditionalCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public If(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
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
    Avatar avatar = getAvatar(ID);
    Direction direction = getDirection(getParameters().get("direction"));
    int newX = avatar.getXCoord() + direction.getXDel();
    int newY = avatar.getYCoord() + direction.getYDel();
    TileData tileToCheck = getElementInformationBundle().getTileData(newX, newY);

    // equals or not equals
    //System.out.println(getParameters());
    String condition = getParameters().get("comparator");
    boolean equalsOrNot = !condition.equals("equal");

    // TODO: refactor using reflection
    boolean result;
    String thingToCheck = getParameters().get("target");
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
    } else {
      if (!getElementInformationBundle().getMapOfCommandLines().isEmpty()) {
        avatar.setProgramCounter(
            getElementInformationBundle().getMapOfCommandLines().get(avatar.getProgramCounter()));
      }

    }
  }
}