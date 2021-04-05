package ooga.model.grid.gridData;

import java.util.List;
import java.util.Map;

public class GoalState extends BoardState {

  public GoalState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData) {
    super(allAvatarLocations, allBlockData);
  }


  public boolean checkGameEnded() {
    //TODO Fill in logic later
    return false;
  }
}
