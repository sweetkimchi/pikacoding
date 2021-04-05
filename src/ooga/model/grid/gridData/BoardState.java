package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BoardState {
  private Map<String, List<Integer>> allAvatarLocations;
  private Map<String, BlockData> allBlockData;
  public BoardState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData) {
    this.allAvatarLocations = allAvatarLocations;
    this.allBlockData = allBlockData;
  }



  public Map<String, List<Integer>> getAllAvatarLocations() {
    return Collections.unmodifiableMap(allAvatarLocations);
  }

  public Map<String, BlockData> getAllBlockData() {
    return Collections.unmodifiableMap(allBlockData);
  }

}
