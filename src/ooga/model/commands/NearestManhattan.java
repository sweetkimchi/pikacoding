package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.grid.gridData.BlockData;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

public class NearestManhattan extends AICommands{

  private int X = 0;
  private int Y = 1;
  public NearestManhattan(ElementInformationBundle elementInformationBundle,
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

    System.out.println("Parameter: " + getParameters().get("target"));
    System.out.println("Nearest: " + closestID);

    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }

  private void stepTowardsClosestAvailableTile(int ID, BlockData block) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);

    int newX = avatar.getXCoord();
    int newY = avatar.getYCoord();
    int newBlockX = block.getLocation().get(X);
    int newBlockY = block.getLocation().get(Y);

    Direction direction = getDirection(calculateDirection(ID, newX, newY, newBlockX, newBlockY));

    Tile prevTile = getCurrTile(ID);
    Tile nextTile = getNextTile(ID, direction);
    //System.out.println(nextTile.getStructure());

    newX = avatar.getXCoord() + direction.getXDel();
    newY = avatar.getYCoord() + direction.getYDel();

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
