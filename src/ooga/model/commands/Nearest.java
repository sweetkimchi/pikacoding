package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.grid.gridData.BlockData;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

public class Nearest extends AICommands{

  private int X = 0;
  private int Y = 1;
  public Nearest(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);

    int minDistance = 10000;
    int xAvatar = avatar.getXCoord();
    int yAvatar = avatar.getYCoord();
    int closestID = -1;
    BlockData closestBlockData = null;
    for(BlockData blockData : getElementInformationBundle().getBlockData()){
      int xBlock = blockData.getLocation().get(X);
      int yBlock = blockData.getLocation().get(Y);
      int manhattanDistance = Math.abs(xAvatar - xBlock) + Math.abs(yAvatar - yBlock);
      if(manhattanDistance < minDistance){
        minDistance = manhattanDistance;
        closestID = blockData.getId();
        closestBlockData = blockData;
      }
    }
    stepTowardsClosestAvailableTile(ID, closestBlockData);
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }

  private void stepTowardsClosestAvailableTile(int ID, BlockData block) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    Direction direction = getDirection(calculateDirection(ID, avatar.getXCoord(), avatar.getYCoord(), block.getLocation().get(X), block.getLocation().get(Y)));
    Tile prevTile = getCurrTile(ID);
    Tile nextTile = getNextTile(ID, direction);
    int newX = avatar.getXCoord() + direction.getXDel();
    int newY = avatar.getYCoord() + direction.getYDel();

    nextTile.add(avatar);
    prevTile.removeAvatar();
    avatar.setXCoord(newX);
    avatar.setYCoord(newY);
    if (avatar.hasBlock()) {
      avatar.getHeldItem().setXCoord(newX);
      avatar.getHeldItem().setYCoord(newY);
      sendBlockPositionUpdate(avatar.getHeldItem());
    }

    sendAvatarPositionUpdate(avatar);
  }

  private String calculateDirection(int ID, int newX, int newY, int newBlockX, int newBlockY) {
    if(newX < newBlockX && getNextTile(ID, getDirection("right")).canAddAvatar()){
      return "right";
    }else if(newBlockX < newX && getNextTile(ID, getDirection("left")).canAddAvatar()){
      return "left";
    } else if(newY < newBlockY && getNextTile(ID, getDirection("down")).canAddAvatar()){
      return "down";
    } else if(newY > newBlockY && getNextTile(ID, getDirection("up")).canAddAvatar()){
      return "up";
    } else{
      return "self";
    }
  }
}
