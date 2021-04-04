package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import ooga.model.commands.Commands;


public  class BoardState {

  private Map<String, List<Integer>> allAvatarLocations;
  private Map<String, BlockData> allBlockData;
  private List<Commands> commandsAvailable;
  private Map<String, String> imageLocations;
  private String description;
  private int numPeople;
  private int level;

  public BoardState(Map<String, List<Integer>> allAvatarLocations,
      Map<String, BlockData> allBlockData, List<Commands> commandsAvailable,
      Map<String, String> imageLocations, String description,
      int numPeople, int level) {
    this.allAvatarLocations = allAvatarLocations;
    this.allBlockData = allBlockData;
    this.commandsAvailable = commandsAvailable;
    this.imageLocations = imageLocations;
    this.description = description;
    this.numPeople = numPeople;
    this.level = level;

  }


  public Map<String, List<Integer>> getAllAvatarLocations() {
    return Collections.unmodifiableMap(allAvatarLocations);
  }

  public Map<String, BlockData> getAllBlockData() {
    return Collections.unmodifiableMap(allBlockData);
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
