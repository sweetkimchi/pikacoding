package ooga.model.grid.gridData;

import java.util.List;
import java.util.Map;
import ooga.model.grid.GameGrid;

public class GoalState extends BoardState {
  private int numOfCommands;
  public GoalState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, int numOfCommands) {
    super(allAvatarLocations, allBlockData);
    this.numOfCommands = numOfCommands;
  }




  public boolean checkGameEnded(GameGrid currentGrid) {
    for (String id: super.getAllAvatarLocations().keySet()) {
      if (!super.getAllAvatarLocations().get(id).equals(currentGrid.getAvatarCoords(Integer.parseInt(id))))  {
        return false;
      }
    }

    for (String id: super.getAllBlockData().keySet()) {
      BlockData blockData = super.getAllBlockData().get(id);
      currentGrid.getTile(blockData.getLocation().get(0),
          blockData.getLocation().get(1));
    }
    return true;
  }
}
