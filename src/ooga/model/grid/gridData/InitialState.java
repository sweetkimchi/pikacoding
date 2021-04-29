package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * {@inheritDoc}
 * Initial state of the board that extends BoardState.
 *
 */
public class InitialState extends BoardState {


  private final List<String> commandsAvailable;
  private final Map<String, String> imageLocations;
  private final String description;
  private final int numPeople;
  private final int level;
  private int timeLimit;

  /**
   *  {@inheritDoc}
   * @param allAvatarLocations map of the avatar data
   * @param allBlockData map of the block data
   * @param commandsAvailable commands avaiable for current level
   * @param imageLocations map for the image locations
   * @param description a description of the game
   * @param numPeople number of avatgars
   * @param level current level player is on
   * @param timeLimit time limit player has
   * @param playerID ID of player
   */
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


  /**
   * gets list of commands avaiable
   * @return list of commands avaiable
   */
  public List<String> getCommandsAvailable() {
    return Collections.unmodifiableList(commandsAvailable);
  }

  /**
   * get list of images available
   * @return list of images avaiable
   */
  public Map<String, String> getImageLocations() {
    return Collections.unmodifiableMap(imageLocations);
  }

  /**
   * get description of level.
   * @return description of level
   */
  public String getDescription() {
    return description;
  }

  /**
   * get number of avatars in the level
   * @return number of avatars
   */
  public int getNumPeople() {
    return numPeople;
  }

  /**
   * get level of the game
   * @return level of the game
   */
  public int getLevel() {
    return level;
  }

  /**
   * get time limit of the game.
   * @return time limit of the game
   */
  public int getTimeLimit() {
    return timeLimit;
  }

}
