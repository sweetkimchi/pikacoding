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
   * Default constructor
   */
  public Step(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * Executes the command on an Avatar.
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
      avatar.setXCoord(newX);
      avatar.setYCoord(newY);
      if (avatar.hasBlock()) {
        avatar.getHeldItem().setXCoord(newX);
        avatar.getHeldItem().setYCoord(newY);
        sendBlockPositionUpdate(avatar.getHeldItem());
      }
    } else {
      //TODO: throw error to handler?
      System.out.println("The avatar cannot step here!");
    }

    sendAvatarPositionUpdate(avatar);

    incrementProgramCounterByOne(avatar);

  }

}