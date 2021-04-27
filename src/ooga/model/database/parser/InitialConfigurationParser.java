package ooga.model.database.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.ModelController;
import ooga.model.commands.AvailableCommands;
import ooga.model.database.FirebaseService;
import ooga.model.exceptions.ExceptionHandler;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;

/**
 * @author billyluqiu
 * Parses initial level configuration at the start of each level.
 * Assumes that data will be well formatted
 * Depends on GameData objects to store Data, and for multiplayer, working firebaseService
 * Call the constructor to parse a file and getterrs to get each attribute of the level
 *
 */
public class InitialConfigurationParser {

  private final int level;
  private final String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES =
      System.getProperty("user.dir") + "/data/gameProperties/";
  private InitialState initialState;
  private GoalState goalState;
  private String description;
  private AvailableCommands availableCommands;
  private AvailableCommands availableCommandsOtherPlayer;
  private ElementInformationBundle elementInformationBundle;
  private GameGridData gameGridData;
  private final FirebaseService firebaseService;
  private final int playerID;

  /**
   * Constructor that takes in a level and parses requisite data files
   * for multiplayer (ID = 1/2) firebaseService must be nonNull,
   * for singleplaeyr (ID = 0), firebaseService must be null
   * @param level level of game to parse
   * @param firebaseService instance of firebase service
   * @param playerID ID of player
   * Throws new exception handler if error raised in parsing the level
   */
  public InitialConfigurationParser(int level, FirebaseService firebaseService, int playerID) {
    this.playerID = playerID;
    this.level = level;
    this.firebaseService = firebaseService;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    if (this.playerID != ModelController.SINGLE_PLAYER) {
      this.parseLevelInfoFromDB();
    } else {
      this.parseLevelInfoFromDataFiles();
    }
  }
  // parses files from data folder for single player
  private void parseLevelInfoFromDataFiles() {

    try {
      String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + this.level + ".json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToLevelInfoFile), HashMap.class);
      HashMap<String, Object> levelInfo = (HashMap<String, Object>) result;
      parseGrid(getMapFromFile("grid.json"));
      parseStartState(getMapFromFile("startState.json"), levelInfo);
      parseEndState(Integer.parseInt((String) levelInfo.get("idealNumOfCommands")),
          getMapFromFile("endState.json"));
      this.description = (String) levelInfo.get("description");
      parseCommands(getMapFromFile("commands.json"), blocksForCurrentPlayer(levelInfo),
          blocksForOtherPlayer(levelInfo));
    } catch (Exception e) {
      throw new ExceptionHandler("error occured while parsing single player data files");
    }
  }

  private Map getMapFromFile(String filePath) throws java.io.IOException {
    String filePathToStartState = rootURLPathForLevel + filePath;
    return new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
  }
  // parses level info from firebase for multiplayer (ensure all players have the same game data)
  private void parseLevelInfoFromDB() {
    try {

      String jsonFromDB = firebaseService.readDBContentsForLevelInit(this.level);
      HashMap result =
          new ObjectMapper().readValue(jsonFromDB, HashMap.class);
      HashMap<String, Object> levelInfo = (HashMap<String, Object>) result.get("levelInfo");
      parseGrid((HashMap) result.get("grid"));
      parseStartState((HashMap) result.get("startState"), levelInfo);
      parseEndState(Integer.parseInt((String) levelInfo.get("idealNumOfCommands")), (HashMap)
          result.get("endState"));
      this.description = (String) levelInfo.get("description");
      parseCommands((HashMap) result.get("commands"), blocksForCurrentPlayer(levelInfo),
          blocksForOtherPlayer(levelInfo));
    } catch (Exception e) {
      throw new ExceptionHandler("error occurred while parsing files from DB");
    }
  }

  private List<String> blocksForCurrentPlayer(Map<String, Object> levelInfo) {
    return switch (this.playerID) {
      case 0 -> (List<String>) levelInfo.get("blocks");
      case 1 -> (List<String>) levelInfo.get("blocks-p1");
      case 2 -> (List<String>) levelInfo.get("blocks-p2");
      default -> null;
    };
  }

  private List<String> blocksForOtherPlayer(Map<String, Object> levelInfo) {
    return switch (this.playerID) {
      case 1 -> (List<String>) levelInfo.get("blocks-p2");
      case 2 -> (List<String>) levelInfo.get("blocks-p1");
      default -> null;
    };
  }

  private void parseStartState(Map startState, Map initial) {
    try {
      this.initialState = new InitialState(
          parseAvatarLocations((Map<String, Object>) startState.get("peopleLocations"), true),
          parseBlockData((Map<String, Object>) startState.get("blocks"), true),
          (List<String>) initial.get("blocks"),
          null,
          (String) initial.get("description"),
          (int) initial.get("numPeople"),
          (int) initial.get("level"),
          Integer.parseInt((String) initial.get("timeLimit")), playerID);
    } catch (Exception e) {
      throw new ExceptionHandler("error parsing start state");
    }
  }

  private void parseEndState(int numOfCommands, Map endState) {
    try {
      this.goalState = new GoalState(
          parseAvatarLocations((Map<String, Object>) endState.get("peopleLocations"), false),
          parseBlockData((Map<String, Object>) endState.get("blocks"), false), numOfCommands,
          (int) endState.get("idealTime"),
          (int) endState.get("idealLines"));
    } catch (Exception e) {
      throw new ExceptionHandler("error parsing end state");
    }
  }

  private Map<String, List<Integer>> parseAvatarLocations(Map<String, Object> peopleLocations,
      boolean addToGameGrid) {
    Map<String, List<Integer>> mapOfPeople = new HashMap<>();
    if (peopleLocations.containsKey("noLoc")) {
      return mapOfPeople;
    }
    for (String s : peopleLocations.keySet()) {
      List<Integer> avatarLocation = (List<Integer>) peopleLocations.get(s);
      mapOfPeople.put(s, avatarLocation);
      if (addToGameGrid) {
        this.elementInformationBundle
            .addAvatar(new Avatar(Integer.parseInt(s), avatarLocation.get(0), avatarLocation.get(1)));
      }
    }
    return mapOfPeople;
  }

  private Map<String, BlockData> parseBlockData(Map<String, Object> blocks, boolean addToGameGrid) {
    Map<String, BlockData> allBlockData = new HashMap<>();
    for (String s : blocks.keySet()) {
      Map<String, Object> currentBlock = (Map<String, Object>) blocks.get(s);
      List<Integer> blockLoc = (List<Integer>) currentBlock.get("loc");
      BlockData blockData = new BlockData(blockLoc,
          (int) currentBlock.get("num"), Boolean.parseBoolean(
          (String) currentBlock.get("pickedUp")), Integer.parseInt(s));
      allBlockData.put(s, blockData);
      if (addToGameGrid) {
        this.elementInformationBundle.addBlock(new DataCube(Integer.parseInt(s), blockLoc.get(0),
            blockLoc.get(1), (int) currentBlock.get("num")));
      }
    }
    return allBlockData;
  }

  private void parseCommands(Map<String, Object> commands, List<String> commandsForCurrentPlayer,
      List<String> comamndsForOtherPlayer) {
    try {
      Map<String, List<Map<String, List<String>>>> commandsMap = new HashMap<>();
      for (String command : commands.keySet()) {
        List<Map<String, List<String>>> params = new ArrayList<>();
        for (Map<String, List<String>> param : ((Map<String, List<Map<String, List<String>>>>) commands
            .get(command)).get("parameters")) {
          if (param.containsKey("noParams")) {
            continue;
          } else {
            params.add(param);
          }
        }
        commandsMap.put(command, params);
      }
      availableCommands = new AvailableCommands(commandsMap, commandsForCurrentPlayer);
      if (comamndsForOtherPlayer != null) {
        availableCommandsOtherPlayer = new AvailableCommands(commandsMap, comamndsForOtherPlayer);
      }
    } catch (Exception e) {
      throw new ExceptionHandler("Parse Commands failed.");
    }
  }

  private void parseGrid(Map grid) {
    try {
      int width = Integer.parseInt((String) grid.get("width"));
      int height = Integer.parseInt((String) grid.get("height"));
      this.elementInformationBundle = new ElementInformationBundle();
      this.elementInformationBundle.setDimensions(width, height);
      List<List<String>> mapOfGrid;
      if (this.playerID != ModelController.SINGLE_PLAYER) {
        mapOfGrid = (List<List<String>>) grid.get("grid");
      } else {
        mapOfGrid = parseSinglePlayerGrid(grid, height);
      }
      for (int i = 0; i < height; i++) {
        List<String> currentRow = mapOfGrid.get(i);
        if (currentRow.size() != width) {
          throw new ExceptionHandler("grid specifications not correct");
        }
        for (int j = 0; j < width; j++) {
          this.elementInformationBundle.setStructure(j, i, Structure.valueOf(currentRow.get(j)));
        }
      }
      this.gameGridData = new GameGridData(this.elementInformationBundle, width, height);
    } catch (Exception e) {
      throw new ExceptionHandler("Error parsing grid");
    }
  }

  private List<List<String>> parseSinglePlayerGrid(Map grid, int height) {
    List<List<String>> mapOfGrid = new ArrayList<>();
    Map<String, List<String>> currentGrid = (Map<String, List<String>>) grid.get("grid");
    for (int i = 0; i < height; i++) {
      mapOfGrid.add(currentGrid.get("" + i));
    }
    return mapOfGrid;
  }

  /**
   * Returns elementInformationBundle after construtor is run
   * @return instance of elementInformationBundle
   */
  public ElementInformationBundle getGameGrid() {
    return this.elementInformationBundle;
  }

  /**
   * Returns goal state after constructor is run
   * @return instance of goalState
   */
  public GoalState getGoalState() {
    return this.goalState;
  }

  /**
   * Returns initial state after constructor is run
   * @return instance of initial state
   */
  public InitialState getInitialState() {
    return this.initialState;
  }

  /**
   * Returns string of the description of the level after constructor of level
   * @return string of description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Get avaiable commands after constructor is run
   * @return avaiblecommands instance
   */
  public AvailableCommands getAvailableCommands() {
    return this.availableCommands;
  }
  /**
   * Get avaiable commands after constructor is run for multiplayer
   * @return avaiblecommands instance that gives other player data
   */
  public AvailableCommands getAvailableCommandsOtherPlayer() {
    return this.availableCommandsOtherPlayer;
  }

  /**
   * Get game grid data after constructor is run
   * @return gameGridData instance
   */
  public GameGridData getGameGridData() {
    return this.gameGridData;
  }

}
