package ooga.model.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.BlockData;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;

public class InitialConfigurationParser {

  private int level;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";
  private InitialState initialState;
  private GoalState goalState;
  private AvailableCommands availableCommands;
  public InitialConfigurationParser(int level)  {
    this.level = level;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    this.parseLevelInfo();
  }

  public GoalState getGoalState() {
    return this.goalState;
  }

  public InitialState getInitialState() {
    return this.initialState;
  }


  public AvailableCommands getAvailableCommands()  {
    return availableCommands;
  }

  private void parseLevelInfo() {
    try {
      String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + this.level + ".json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToLevelInfoFile), HashMap.class);
      parseStartState(result);
      parseEndState(Integer.parseInt((String) result.get("idealNumOfCommands")));
      parseCommands();
    }
    catch (Exception e) {
      //handle later
    }
  }

  private void parseStartState(HashMap initial) {
    try {
      String filePathToStartState = rootURLPathForLevel + "startState.json";
      HashMap result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      this.initialState = new InitialState(
          parseAvatarLocations((Map<String, Object>) result.get("peopleLocations")),
          parseBlockData((Map<String, Object>) result.get("blocks")),
          (List<String>) initial.get("blocks"),
          parseImageLocations((String) initial.get("images")),
          (String) initial.get("description"),
          (int) initial.get("numPeople"),
          (int) initial.get("level"));
    } catch (Exception e) {
      e.printStackTrace();
      //figure out error handling later lol
    }

  }

  private void parseEndState(int numOfCommands)  {
    try {
      String filePathToStartState = rootURLPathForLevel + "endState.json";
      Map<String, Object> result =
          new ObjectMapper().readValue(new FileReader(filePathToStartState), HashMap.class);
      this.goalState = new GoalState(parseAvatarLocations((Map<String, Object>) result.get("peopleLocations")),
      parseBlockData((Map<String, Object>) result.get("blocks")), numOfCommands);
    }
    catch (Exception e) {
      e.printStackTrace();
      //figure out error handling later lol
    }

  }

  private Map<String, List<Integer>> parseAvatarLocations(Map<String, Object> peopleLocations)  {
    Map<String, List<Integer>> mapOfPeople = new HashMap<>();
    for (String s: peopleLocations.keySet())  {
      mapOfPeople.put(s, (List<Integer>) peopleLocations.get(s));
    }
    return mapOfPeople;
  }

  private Map<String, BlockData> parseBlockData(Map<String, Object> blocks) {
    Map<String, BlockData> allBlockData = new HashMap<>();

    for (String s: blocks.keySet()) {
      Map<String, Object> currentBlock = (Map<String, Object>) blocks.get(s);
      BlockData blockData = new BlockData((List<Integer>) currentBlock.get("loc"),
          (int) currentBlock.get("num"), Boolean.parseBoolean(
          (String) currentBlock.get("pickedUp")));
      allBlockData.put(s, blockData);
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
      e.printStackTrace();
      return null;
      //HANDLE LATER
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
      e.printStackTrace();
      //figure out error handling later lol
    }
  }


}
