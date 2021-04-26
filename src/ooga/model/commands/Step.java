package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class Step extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Step(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
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
    Tile prevTile = getCurrTile(ID);
    Tile nextTile = getNextTile(ID, direction);
    //System.out.println(nextTile.getStructure());

    if (nextTile.canAddAvatar()) {
      nextTile.add(avatar);
      prevTile.removeAvatar();
      avatar.setXY(newX, newY);
      if (avatar.hasBlock()) {
        avatar.getHeldItem().setXY(newX, newY);
        sendBlockPositionUpdate(avatar.getHeldItem());
      }
    } else {
      //if desired, handle error for when the avatar tries to step in a disallowed tile
      //System.out.println("The avatar cannot step here!");
    }

    sendAvatarPositionUpdate(avatar);

    incrementProgramCounterByOne(avatar);

  }

}