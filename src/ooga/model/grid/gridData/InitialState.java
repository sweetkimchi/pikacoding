package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class InitialState extends BoardState {


  private final List<String> commandsAvailable;
  private final Map<String, String> imageLocations;
  private final String description;
  private final int numPeople;
  private final int level;
  private int timeLimit;

  public InitialState(Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, List<String> commandsAvailable,
      Map<String, String> imageLocations, String description,
      int numPeople, int level, int timeLimit, int playerID) {
    super(allAvatarLocations, allBlockData);
    this.commandsAvailable = commandsAvailable;
    this.imageLocations = imageLocations;
    this.description = description;
    this.numPeople = numPeople;
    this.level = level;
    this.timeLimit = timeLimit;
    if (playerID == 0) {
      this.timeLimit = Integer.MAX_VALUE;
    }

  }


  public List<String> getCommandsAvailable() {
    return Collections.unmodifiableList(commandsAvailable);
  }

  public Map<String, String> getImageLocations() {
    return Collections.unmodifiableMap(imageLocations);
  }

  public String getDescription() {
    return description;
  }

  public int getNumPeople() {
    return numPeople;
  }

  public int getLevel() {
    return level;
  }

  public int getTimeLimit() {
    return timeLimit;
  }

}
