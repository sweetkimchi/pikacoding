package ooga.model.grid.gridData;

import java.util.List;
import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

public class GoalState extends BoardState {
  private int numOfCommands;
  public GoalState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, int numOfCommands) {
    super(allAvatarLocations, allBlockData);
    this.numOfCommands = numOfCommands;
  }




  public boolean checkGameEnded(ElementInformationBundle currentGrid) {
    for (String id: super.getAllAvatarLocations().keySet()) {
      int x = getAllAvatarLocations().get(id).get(0);
      int y = getAllAvatarLocations().get(id).get(1);
      if (currentGrid.getTileData(x , y).getAvatarId() != Integer.parseInt(id)) {
        return false;
      }
    }
    List<BlockData> currentBlockData = currentGrid.getBlockData();
    for (BlockData block: currentBlockData) {
      if (super.getAllBlockData().containsKey("" + block.getId())) {
        if (!super.getAllBlockData().get("" + block.getId()).equals(block)) {
          System.out.println("block " + block.getId() + " not equal!");
          return false;
        }
      }
      else  {
        return false;
      }
    }
    return true;
  }
}
