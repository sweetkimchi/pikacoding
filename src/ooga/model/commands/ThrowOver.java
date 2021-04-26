package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

public class ThrowOver extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public ThrowOver(ElementInformationBundle elementInformationBundle,
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
    Avatar avatar = getAvatar(ID);
    Direction direction = getDirection(getParameters().get("direction"));
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    int nextX = currX + direction.getXDel();
    int nextY = currY + direction.getYDel();
    int afterX = nextX + direction.getXDel();
    int afterY = nextY + direction.getYDel();
    Tile currTile = getElementInformationBundle().getTile(currX, currY);
    Tile nextTile = getElementInformationBundle().getTile(nextX, nextY);
    Tile afterTile = getElementInformationBundle().getTile(afterX, afterY);

    if (!avatar.hasBlock()) {
      //if desired, handle error if the avatar is not holding a block to throw
      //System.out.println("You are not holding a block!");
    } else if (afterTile != null && afterTile.canAddBlock()) {
      transferBlockToTile(avatar, afterTile, afterX, afterY);
    } else if (nextTile != null && nextTile.canAddBlock()) {
      transferBlockToTile(avatar, nextTile, nextX, nextY);
    } else if (currTile.canAddBlock()) {
      transferBlockToTile(avatar, currTile, currX, currY);
    } else {
      //if desired, handle error if avatar has no space for the block to be thrown
      //System.out.println("You cannot throw!");
    }

    incrementProgramCounterByOne(avatar);
  }

  private void transferBlockToTile(Avatar avatar, Tile tile, int tileX, int tileY) {
    Block block = avatar.drop();
    block.drop();
    tile.add(block);
    block.setXY(tileX, tileY);

    sendBlockHeldUpdate(block);
    sendBlockPositionUpdate(block);
  }
}
