package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

public class ThrowOver extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public ThrowOver(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

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
      //TODO: throw error to handler
      System.out.println("You are not holding a block!");
    } else if (afterTile != null && afterTile.canAddBlock()) {
      transferBlockToTile(avatar, afterTile);
    } else if (nextTile != null && nextTile.canAddBlock()) {
      transferBlockToTile(avatar, nextTile);
    } else if (currTile.canAddBlock()) {
      transferBlockToTile(avatar, currTile);
    } else {
      //TODO: throw error to handler
      System.out.println("You cannot throw!");
    }

    incrementProgramCounterByOne(avatar);
  }

  private void transferBlockToTile(Avatar avatar, Tile afterTile) {
    Block block = avatar.drop();
    block.drop();
    afterTile.add(block);

    sendBlockHeldUpdate(block);
    sendBlockPositionUpdate(block);
  }
}
