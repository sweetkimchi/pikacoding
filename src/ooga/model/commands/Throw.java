package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

/**
 * Throw is a type of Basic Command that directs the given avatar to throw the block it is holding
 * in a given direction as far as possible (similar to how a queen moves in chess). If the block
 * runs into a tile that it cannot be placed, then it will stop at the previous valid tile. If the
 * current tile and the next tile both cannot accept the block, or the avatar is not holding a
 * block, then the avatar will do nothing.
 *
 * @author Harrison Huang
 */
public class Throw extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Throw(ElementInformationBundle elementInformationBundle,
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
    int newX = currX + direction.getXDel();
    int newY = currY + direction.getYDel();
    Tile currTile = getCurrTile(ID);
    Tile nextTile = getNextTile(ID, direction);
    if (!avatar.hasBlock()) {
      //if desired, handle error if the avatar is not holding a block to throw
      //System.out.println("You are not holding a block!");
    }
    if (currTile.canAddBlock() || nextTile.canAddBlock()) {

      Block block = avatar.drop();
      if (block != null) {

        block.drop();
      }

      while (block != null) {
        if (nextTile == null || !nextTile.canAddBlock() || direction == Direction.CURRENT) {
          currTile.add(block);
          block.setXY(currX, currY);
          break;
        }
        currTile = nextTile;
        currX = newX;
        currY = newY;
        newX += direction.getXDel();
        newY += direction.getYDel();
        nextTile = getElementInformationBundle().getTile(newX, newY);
      }
      if (block != null) {
        sendBlockHeldUpdate(block);
        sendBlockPositionUpdate(block);
      }

    } else {
      //if desired, handle error if avatar has no space for the block to be thrown
      //System.out.println("You cannot throw!");

    }
    incrementProgramCounterByOne(avatar);

  }
}
