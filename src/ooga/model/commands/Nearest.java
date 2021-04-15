package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.gridData.BlockData;
import ooga.model.player.Avatar;

public class Nearest extends AICommands{

  public Nearest(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);

    int distance = 10000;
    int xAvatar = avatar.getXCoord();
    int yAvatar = avatar.getYCoord();
    int closestID = -1;
    for(BlockData blockData : getElementInformationBundle().getBlockData()){
      int xBlock = blockData.getLocation().get(0);
      int yBlock = blockData.getLocation().get(1);
      int manhattanDistance = Math.abs(xAvatar - xBlock) + Math.abs(yAvatar - yBlock);
      if(manhattanDistance < distance){
        distance = manhattanDistance;
        closestID = blockData.getId();
      }
    }

    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }
}
