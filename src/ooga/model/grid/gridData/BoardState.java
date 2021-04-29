package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author billyluqiu
 * Abstract class that represents board state including the lcoation of avatars and blocks.
 *
 * No dependencies
 * No assumptions
 */
public abstract class BoardState {

  private final Map<String, List<Integer>> allAvatarLocations;
  private final Map<String, BlockData> allBlockData;

  /**
   * Constructor that sets data varaibles.
   * @param allAvatarLocations map representing avatar locations from their id.
   * @param allBlockData map representing block data from their ID.
   */
  public BoardState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData) {
    this.allAvatarLocations = allAvatarLocations;
    this.allBlockData = allBlockData;
  }

  /**
   * getter method for avatar locations.
   * @return avatar locations indexed by their ID.
   */
  public Map<String, List<Integer>> getAllAvatarLocations() {
    return Collections.unmodifiableMap(allAvatarLocations);
  }

  /**
   * getter method for block data.
   * @return blcock data with their ID as index.
   */
  public Map<String, BlockData> getAllBlockData() {
    return Collections.unmodifiableMap(allBlockData);
  }

}
