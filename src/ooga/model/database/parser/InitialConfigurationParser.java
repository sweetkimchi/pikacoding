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

public class InitialConfigurationParser {

  private int level;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";
  private InitialState initialState;
  private GoalState goalState;
  private String description;
  private AvailableCommands availableCommands;
  private AvailableCommands availableCommandsOtherPlayer;
  private ElementInformationBundle elementInformationBundle;
  private boolean errorOccurred = false;
  private String errorMessage = "";
  private GameGridData gameGridData;
  private FirebaseService firebaseService;
  private int playerID;

  public InitialConfigurationParser(int level, FirebaseService firebaseService, int playerID)  {
    this.playerID = playerID;
    this.level = level;
    this.firebaseService = firebaseService;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    if (this.playerID != ModelController.SINGLE_PLAYER) {
      this.parseLevelInfoFromDB();
    }
    else  {
      this.parseLevelInfoFromDataFiles();
    }
  }

  private void parseLevelInfoFromDataFiles()  {

    try {
      String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + this.level + ".json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToLevelInfoFile), HashMap.class);
      HashMap<String, Object> levelInfo = (HashMap<String, Object>) result;
      parseGrid(getMapFromFile("grid.json"));
      parseStartState(getMapFromFile("startState.json"), levelInfo);
      parseEndState(Integer.parseInt((String) levelInfo.get("idealNumOfCommands")), getMapFromFile("endState.json"));
      this.description = (String) levelInfo.get("description");
      parseCommands(getMapFromFile("commands.json"), blocksForCurrentPlayer(levelInfo),
          blocksForOtherPlayer(levelInfo));
    }
    catch (Exception e) {
      //handle later
    }
  }

  private Map getMapFromFile(String filePath) throws java.io.IOException {
    String filePathToStartState = rootURLPathForLevel + filePath;
    Map result =
        new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
    return result;
  }

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
    }
    catch (Exception e) {
      this.errorMessage = "Error parsing level file";
      this.errorOccurred = true;
    }
  }



  private List<String> blocksForCurrentPlayer(Map<String, Object> levelInfo) {
    var result = switch (this.playerID) {
      case 0 -> (List<String>) levelInfo.get("blocks");
      case 1 -> (List<String>) levelInfo.get("blocks-p1");
      case 2 -> (List<String>) levelInfo.get("blocks-p2");
      default -> null;
    };
    return result;
  }

  private List<String> blocksForOtherPlayer(Map<String, Object> levelInfo)  {
    var result = switch (this.playerID) {
      case 0 -> null;
      case 1 -> (List<String>) levelInfo.get("blocks-p2");
      case 2 -> (List<String>) levelInfo.get("blocks-p1");
      default -> null;
    };
    return result;
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
      e.printStackTrace();
      this.errorMessage = "Error parsing start state";
      this.errorOccurred = true;
    }

  }

  private void parseEndState(int numOfCommands, Map endState)  {
    try {
      this.goalState = new GoalState(parseAvatarLocations((Map<String, Object>) endState.get("peopleLocations"), false),
      parseBlockData((Map<String, Object>) endState.get("blocks"), false), numOfCommands,
          (int) endState.get("idealTime"),
          (int) endState.get("idealLines"));
    }
    catch (Exception e) {
      e.printStackTrace();
      this.errorMessage = "Error parsing end state";
      this.errorOccurred = true;
    }

  }

  private Map<String, List<Integer>> parseAvatarLocations(Map<String, Object> peopleLocations, boolean addToGameGrid)  {
    Map<String, List<Integer>> mapOfPeople = new HashMap<>();
    if (peopleLocations.containsKey("noLoc")) {
      return mapOfPeople;
    }
    for (String s: peopleLocations.keySet())  {
      List<Integer> avatarLocation = (List<Integer>) peopleLocations.get(s);
      mapOfPeople.put(s, avatarLocation);
      if (addToGameGrid) {
        this.elementInformationBundle.addAvatar(new Avatar(Integer.parseInt(s), avatarLocation.get(0),
            avatarLocation.get(1)));
      }
    }
    return mapOfPeople;
  }

  private Map<String, BlockData> parseBlockData(Map<String, Object> blocks, boolean addToGameGrid) {
    Map<String, BlockData> allBlockData = new HashMap<>();

    for (String s: blocks.keySet()) {
      Map<String, Object> currentBlock = (Map<String, Object>) blocks.get(s);
      List<Integer> blockLoc = (List<Integer>) currentBlock.get("loc");
      BlockData blockData = new BlockData(blockLoc,
          (int) currentBlock.get("num"), Boolean.parseBoolean(
          (String) currentBlock.get("pickedUp")), Integer.parseInt(s));
      allBlockData.put(s, blockData);
      if (addToGameGrid)  {
        this.elementInformationBundle.addBlock(new DataCube(Integer.parseInt(s), blockLoc.get(0),
            blockLoc.get(1), (int) currentBlock.get("num")));
      }

    }
    return allBlockData;
  }

//  private Map<String, String> parseImageLocations(String imageLocations) {
//    try {
//      String filePathToStartState = rootURLPathForLevel + imageLocations;
//      HashMap result =
//          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
//      return (HashMap<String, String>) result;
//    }
//    catch (Exception e) {
//      this.errorMessage = "Error parsing image locations";
//      this.errorOccurred = true;
//      return null;
//    }
//  }

  private void parseCommands(Map<String, Object> commands, List<String> commandsForCurrentPlayer,
      List<String> comamndsForOtherPlayer)  {
    try {
      Map<String, List<Map<String, List<String>>>> commandsMap = new HashMap<>();
      for (String command: commands.keySet()) {
        List<Map<String, List<String>>> params = new ArrayList<>();
        for (Map<String, List<String>> param:((Map<String, List<Map<String, List<String>>>>) commands.get(command)).get("parameters")) {
          if (param.containsKey("noParams"))  {
            continue;
          }
          else  {
            params.add(param);
          }
        }
        commandsMap.put(command, params);
      }
      availableCommands = new AvailableCommands(commandsMap, commandsForCurrentPlayer);
      if (comamndsForOtherPlayer != null) {
        availableCommandsOtherPlayer = new AvailableCommands(commandsMap, comamndsForOtherPlayer);
      }
    }
    catch (Exception e) {
      throw new ExceptionHandler("Parse Commands failed.");
    }
  }

  private void parseGrid(Map grid)  {
    try {
      int width = Integer.parseInt((String)grid.get("width"));
      int height = Integer.parseInt((String)grid.get("height"));
      this.elementInformationBundle = new ElementInformationBundle();
      this.elementInformationBundle.setDimensions(width, height);
      List<List<String>> mapOfGrid;
      if (this.playerID != ModelController.SINGLE_PLAYER) {
         mapOfGrid = (List<List<String>>) grid.get("grid");
      }
      else  {
        mapOfGrid = parseSinglePlayerGrid(grid, height);
      }
      for (int i = 0; i < height; i++) {
        List<String> currentRow = mapOfGrid.get(i);
        if (currentRow.size() != width) {
          errorOccurred = true;
          errorMessage = "Error parsing grid dimensions";
          return;
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

  private List<List<String>>  parseSinglePlayerGrid(Map grid, int height)  {
    List<List<String>> mapOfGrid = new ArrayList<List<String>>();
    Map<String, List<String>> currentGrid = (Map<String, List<String>>) grid.get("grid");
    for (int i = 0; i < height; i++)  {
      mapOfGrid.add(currentGrid.get("" + i));
    }
    return mapOfGrid;

  }


  public boolean getErrorOccurred()  {
    return this.errorOccurred;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public ElementInformationBundle getGameGrid() { return this.elementInformationBundle; }

  public GoalState getGoalState() {
    return this.goalState;
  }

  public InitialState getInitialState() {
    return this.initialState;
  }

  public String getDescription() {
    return this.description;
  }

  public AvailableCommands getAvailableCommands()  {
    return this.availableCommands;
  }

  public AvailableCommands getAvailableCommandsOtherPlayer()  {
    return this.availableCommandsOtherPlayer;
  }

  public GameGridData getGameGridData() {
    return this.gameGridData;
  }

}
