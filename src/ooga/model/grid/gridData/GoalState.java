package ooga.model.grid.gridData;

import java.util.List;
import java.util.Map;
import ooga.model.grid.ElementInformationBundle;

/**
 * {@inheritDoc}
 * This class extends boardState by setting the goal state data objects including ideal conditions.
 */
public class GoalState extends BoardState {

  private final int numOfCommands;
  private final int idealTime;
  private final int idealLines;

  /**
   * {@inheritDoc}
   * @param allAvatarLocations locations of avatars.
   * @param allBlockData locatins of blocks
   * @param numOfCommands number of commands that are ideal for the level
   * @param idealTime ideal amount of time
   * @param idealLines ideal number of command lines to use.
   */
  public GoalState(
      Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, int numOfCommands,
      int idealTime,
      int idealLines) {
    super(allAvatarLocations, allBlockData);
    this.numOfCommands = numOfCommands;
    this.idealLines = idealLines;
    this.idealTime = idealTime;
  }

  /**
   * Checks whether game has ended or not by checking grid State with the goal state.
   * @param currentGrid current state of the Grid
   * @return true if goal state is the same as the current state, false otherwise
   */
  public boolean checkGameEnded(ElementInformationBundle currentGrid) {
    for (String id : super.getAllAvatarLocations().keySet()) {
      int x = getAllAvatarLocations().get(id).get(0);
      int y = getAllAvatarLocations().get(id).get(1);
      if (currentGrid.getTileData(x, y).getAvatarId() != Integer.parseInt(id)) {
        return false;
      }
    }
    List<BlockData> currentBlockData = currentGrid.getBlockData();
    for (BlockData block : currentBlockData) {
      if (super.getAllBlockData().containsKey("" + block.getId())) {
        if (!super.getAllBlockData().get("" + block.getId()).equals(block)) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * getter method for ideal time.
   * @return ideal time.
   */
  public int getIdealTime() {
    return idealTime;
  }

  /**
   * getter method for ideal num of lines
   * @return ideal num of lines.
   */
  public int getIdealLines() {
    return idealLines;
  }

  /**
   * getter method for idea num of commands
   * @return ideal num of commands.
   */
  public int getNumOfCommands() {
    return this.numOfCommands;
  }
}
