package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.grid.gridData.BlockData;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class Nearest extends AICommands {

  private final int X = 0;
  private final int Y = 1;

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Nearest(ElementInformationBundle elementInformationBundle,
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
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);

    int minDistance = Integer.MAX_VALUE;
    int xAvatar = avatar.getXCoord();
    int yAvatar = avatar.getYCoord();
    BlockData closestBlockData = null;
    for (BlockData blockData : getElementInformationBundle().getBlockData()) {
      int xBlock = blockData.getLocation().get(X);
      int yBlock = blockData.getLocation().get(Y);
      int manhattanDistance = Math.abs(xAvatar - xBlock) + Math.abs(yAvatar - yBlock);
      if (manhattanDistance < minDistance) {
        minDistance = manhattanDistance;
        closestBlockData = blockData;
      }
    }
    stepTowardsClosestAvailableTile(ID, closestBlockData);
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }

  private void stepTowardsClosestAvailableTile(int ID, BlockData block) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    int xBlock = block.getLocation().get(X);
    int yBlock = block.getLocation().get(Y);
    int initialManhattanDistance = Integer.MAX_VALUE;
    int newX = avatar.getXCoord();
    int newY = avatar.getYCoord();
    Tile prevTile = getCurrTile(ID);
    Tile nextTile = getNextTile(ID, Direction.CURRENT);
    if (!(xBlock == newX && yBlock == newY)) {
      for (Direction direction : Direction.values()) {
        int dummyX = avatar.getXCoord() + direction.getXDel();
        int dummyY = avatar.getYCoord() + direction.getYDel();
        int manhattanDistance = Math.abs(dummyX - xBlock) + Math.abs(dummyY - yBlock);
        if (manhattanDistance < initialManhattanDistance && getNextTile(ID, direction)
            .canAddAvatar() && direction != Direction.CURRENT) {
          initialManhattanDistance = manhattanDistance;
          newX = dummyX;
          newY = dummyY;
          nextTile = getNextTile(ID, direction);
        }
      }
    }

    moveAvatar(avatar, prevTile, nextTile, newX, newY);
    sendAvatarPositionUpdate(avatar);
  }

  private void moveAvatar(Avatar avatar, Tile prevTile, Tile nextTile, int newX, int newY) {
    nextTile.add(avatar);
    prevTile.removeAvatar();
    avatar.setXY(newX, newY);
    if (avatar.hasBlock()) {
      avatar.getHeldItem().setXY(newX, newY);
      sendBlockPositionUpdate(avatar.getHeldItem());
    }
  }
}
