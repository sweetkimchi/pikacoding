package ooga.model.grid.gridData;

import java.util.List;
import java.util.Map;

public class GoalState extends BoardState {
  private int numOfCommands;
  public GoalState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, int numOfCommands) {
    super(allAvatarLocations, allBlockData);
    this.numOfCommands = numOfCommands;
  }


  public boolean checkGameEnded() {
    //TODO Fill in logic later
    return false;
  }
}
