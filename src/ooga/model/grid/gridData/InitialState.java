package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import ooga.model.commands.Commands;


public  class InitialState extends BoardState {


  private List<String> commandsAvailable;
  private Map<String, String> imageLocations;
  private String description;
  private int numPeople;
  private int level;
  private int timeLimit;

  public InitialState(Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, List<String> commandsAvailable,
      Map<String, String> imageLocations, String description,
      int numPeople, int level, int timeLimit) {
    super(allAvatarLocations, allBlockData);
    this.commandsAvailable = commandsAvailable;
    this.imageLocations = imageLocations;
    this.description = description;
    this.numPeople = numPeople;
    this.level = level;
    this.timeLimit = timeLimit;

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
