package ooga.model.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.Avatar;
import ooga.model.player.Datacube;

public class InitialConfigurationParser {

  private int level;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";
  private InitialState initialState;
  private GoalState goalState;
  private AvailableCommands availableCommands;
  private GameGrid gameGrid;
  private boolean errorOccurred = false;
  private String errorMessage = "";
  private GameGridData gameGridData;

  public InitialConfigurationParser(int level)  {
    this.level = level;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    this.parseLevelInfo();
  }

  public boolean getErrorOccurred()  {
    return this.errorOccurred;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public GameGrid getGameGrid() { return this.gameGrid; }

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

  private void parseLevelInfo() {
    try {
      String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + this.level + ".json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToLevelInfoFile), HashMap.class);
      parseGrid((String) result.get("grid"));
      parseStartState(result);
      parseEndState(Integer.parseInt((String) result.get("idealNumOfCommands")));
      parseCommands();
    }
    catch (Exception e) {
      this.errorMessage = "Error parsing level file";
      this.errorOccurred = true;
    }
  }

  private void parseStartState(HashMap initial) {
    try {
      String filePathToStartState = rootURLPathForLevel + "startState.json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      this.initialState = new InitialState(
          parseAvatarLocations((Map<String, Object>) result.get("peopleLocations"), true),
          parseBlockData((Map<String, Object>) result.get("blocks"), true),
          (List<String>) initial.get("blocks"),
          parseImageLocations((String) initial.get("images")),
          (String) initial.get("description"),
          (int) initial.get("numPeople"),
          (int) initial.get("level"));
    } catch (Exception e) {
      this.errorMessage = "Error parsing start state";
      this.errorOccurred = true;
    }

  }

  private void parseEndState(int numOfCommands)  {
    try {
      String filePathToStartState = rootURLPathForLevel + "endState.json";
      Map<String, Object> result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      this.goalState = new GoalState(parseAvatarLocations((Map<String, Object>) result.get("peopleLocations"), false),
      parseBlockData((Map<String, Object>) result.get("blocks"), false), numOfCommands);
    }
    catch (Exception e) {
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
        this.gameGrid.addGameElement(new Avatar(Integer.parseInt(s), avatarLocation.get(0),
            avatarLocation.get(1)), avatarLocation.get(0), avatarLocation.get(1));
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
          (String) currentBlock.get("pickedUp")));
      allBlockData.put(s, blockData);
      if (addToGameGrid)  {
        this.gameGrid.addGameElement(new Datacube(Integer.parseInt(s), blockLoc.get(0),
            blockLoc.get(1)), blockLoc.get(0), blockLoc.get(1));
      }

    }
    return allBlockData;
  }

  private Map<String, String> parseImageLocations(String imageLocations) {
    try {
      String filePathToStartState = rootURLPathForLevel + imageLocations;
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      return (HashMap<String, String>) result;
    }
    catch (Exception e) {
      this.errorMessage = "Error parsing image locations";
      this.errorOccurred = true;
      return null;
    }
  }

  private void parseCommands()  {
    try {
      String filePathToStartState = rootURLPathForLevel + "commands.json";
      Map<String, Object> result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      Map<String, List<Map<String, List<String>>>> commandsMap = new HashMap<>();
      for (String command: result.keySet()) {
        List<Map<String, List<String>>> params = new ArrayList<>();
        for (Map<String, List<String>> param:((Map<String, List<Map<String, List<String>>>>) result.get(command)).get("parameters")) {
          params.add(param);
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

  private void parseGrid(String gridFileName)  {
    String filePathToStartState = rootURLPathForLevel + gridFileName;
    try {
      Map<String, Object> result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      int width = Integer.parseInt((String)result.get("width"));
      int height = Integer.parseInt((String)result.get("height"));
      this.gameGrid = new GameGrid();
      this.gameGrid.setDimensions(width, height);
      Map<String, List<String>> mapOfGrid = (Map<String, List<String>>) result.get("grid");
      for (int i = 0; i < height; i++) {
        List<String> currentRow = mapOfGrid.get("" + i );
        if (currentRow.size() != width) {
          errorOccurred = true;
          errorMessage = "Error parsing grid dimensions";
          return;
        }
        for (int j = 0; j < width; j++) {
          this.gameGrid.setStructure(j, i, Structure.valueOf(currentRow.get(j)));
        }
      }
      this.gameGridData = new GameGridData(this.gameGrid, width, height);
    } catch (IOException e) {
      this.errorMessage = "Error parsing grid";
      this.errorOccurred = true;
    }
  }


}
