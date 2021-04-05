package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import ooga.model.commands.Commands;


public  class InitialState extends BoardState {


  private List<Commands> commandsAvailable;
  private Map<String, String> imageLocations;
  private String description;
  private int numPeople;
  private int level;

  public InitialState(Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, List<Commands> commandsAvailable,
      Map<String, String> imageLocations, String description,
      int numPeople, int level) {
    super(allAvatarLocations, allBlockData);
    this.commandsAvailable = commandsAvailable;
    this.imageLocations = imageLocations;
    this.description = description;
    this.numPeople = numPeople;
    this.level = level;

  }


  public List<Commands> getCommandsAvailable() {
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

}
