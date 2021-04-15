package ooga.model.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.commands.AvailableCommands;
import ooga.model.database.FirebaseService;
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
  private AvailableCommands availableCommands;
  private ElementInformationBundle elementInformationBundle;
  private boolean errorOccurred = false;
  private String errorMessage = "";
  private GameGridData gameGridData;
  private FirebaseService firebaseService;

  public InitialConfigurationParser(int level, FirebaseService firebaseService)  {
    this.level = level;
    this.firebaseService = firebaseService;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    this.parseLevelInfo();
  }

  private void parseLevelInfo() {
    try {

      String jsonFromDB = firebaseService.readDBContentsForLevelInit(this.level);
      HashMap result =
          new ObjectMapper().readValue(jsonFromDB, HashMap.class);
      HashMap<String, Object> levelInfo = (HashMap<String, Object>) result.get("levelInfo");
      parseGrid((HashMap) result.get("grid"));
      parseStartState((HashMap) result.get("startState"), levelInfo);
      parseEndState(Integer.parseInt((String) levelInfo.get("idealNumOfCommands")), (HashMap)
          result.get("endState"));
      parseCommands((HashMap) result.get("commands"));
    }
    catch (Exception e) {
      this.errorMessage = "Error parsing level file";
      this.errorOccurred = true;
    }
  }

  private void parseStartState(HashMap startState, HashMap initial) {
    try {
      this.initialState = new InitialState(
          parseAvatarLocations((Map<String, Object>) startState.get("peopleLocations"), true),
          parseBlockData((Map<String, Object>) startState.get("blocks"), true),
          (List<String>) initial.get("blocks"),
          null,
          (String) initial.get("description"),
          (int) initial.get("numPeople"),
          (int) initial.get("level"));
    } catch (Exception e) {
      e.printStackTrace();
      this.errorMessage = "Error parsing start state";
      this.errorOccurred = true;
    }

  }

  private void parseEndState(int numOfCommands, HashMap endState)  {
    try {
      this.goalState = new GoalState(parseAvatarLocations((Map<String, Object>) endState.get("peopleLocations"), false),
      parseBlockData((Map<String, Object>) endState.get("blocks"), false), numOfCommands);
    }
    catch (Exception e) {
      e.printStackTrace();
      this.errorMessage = "Error parsing end state";
      this.errorOccurred = true;
    }

  }

  private Map<String, List<Integer>> parseAvatarLocations(Map<String, Object> peopleLocations, boolean addToGameGrid)  {
    Map<String, List<Integer>> mapOfPeople = new HashMap<>();
    for (String s: peopleLocations.keySet())  {
      List<Integer> avatarLocation = (List<Integer>) peopleLocations.get(s);
      mapOfPeople.put(s, avatarLocation);
      if (addToGameGrid) {
        this.elementInformationBundle.addGameElement(new Avatar(Integer.parseInt(s), avatarLocation.get(0),
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
        this.elementInformationBundle.addGameElement(new DataCube(Integer.parseInt(s), blockLoc.get(0),
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

  private void parseCommands(HashMap<String, Object> commands)  {
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
      availableCommands = new AvailableCommands(commandsMap);
    }
    catch (Exception e) {
      this.errorMessage = "Error parsing commands";
      this.errorOccurred = true;
    }
  }

  private void parseGrid(HashMap Grid)  {
    try {
      int width = Integer.parseInt((String)Grid.get("width"));
      int height = Integer.parseInt((String)Grid.get("height"));
      this.elementInformationBundle = new ElementInformationBundle();
      this.elementInformationBundle.setDimensions(width, height);
      List<List<String>> mapOfGrid = (List<List<String>>) Grid.get("grid");
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
      e.printStackTrace();
      this.errorMessage = "Error parsing grid";
      this.errorOccurred = true;
    }
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

  public AvailableCommands getAvailableCommands()  {
    return availableCommands;
  }

  public GameGridData getGameGridData() {
    return this.gameGridData;
  }

}
