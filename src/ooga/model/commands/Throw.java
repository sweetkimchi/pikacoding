package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

public class Throw extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Throw(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = getAvatar(ID);
    Direction direction = getDirection(getParameters().get("direction"));
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    int newX = currX + direction.getXDel();
    int newY = currY + direction.getYDel();
    Tile currTile = getElementInformationBundle().getTile(currX,currY);
    Tile nextTile = getElementInformationBundle().getTile(newX,newY);
    if (!avatar.hasBlock()) {
      //TODO: throw error to handler
      System.out.println("You are not holding a block!");
    }
    if (currTile.canAddBlock() || nextTile.canAddBlock() ) {

      Block block = avatar.drop();
      if(block != null){

        block.drop();
      }

      while (block != null) {
        if (nextTile == null || !nextTile.canAddBlock() || direction == Direction.CURRENT) {
          currTile.add(block);
          block.setXCoord(currX);
          block.setYCoord(currY);
          break;
        }
        currTile = nextTile;
        currX = newX;
        currY = newY;
        newX += direction.getXDel();
        newY += direction.getYDel();
        nextTile = getElementInformationBundle().getTile(newX,newY);
      }
      if(block != null){
        sendBlockHeldUpdate(block);
        sendBlockPositionUpdate(block);
      }

    } else {
      //TODO: throw error to handler
      System.out.println("You cannot throw!");

    }

    incrementProgramCounterByOne(avatar);

    //TODO: send updates to ElementInformationBundle

  }
}
