package ooga.model.parser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InitialConfigurationParser {

  private int level;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";


  public InitialConfigurationParser(int level)  {
    this.level = level;
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + this.level + "/";
    parseStates();
  }


  private void parseStates()  {
    parseStartState();
    parseEndState();
  }

  private void parseStartState()  {
    try {
      JSONParser parser = new JSONParser();
      String filePathToStartState = rootURLPathForLevel + "startState.json";
      JSONObject obj = (JSONObject) parser.parse(new FileReader(filePathToStartState));
      parsePeopleLocations((JSONObject) obj.get("peopleLocations"));
    }
    catch (Exception e) {
      e.printStackTrace();
      //figure out error handling later lol
    }

  }

  private void parseEndState()  {
    try {
      JSONParser parser = new JSONParser();
      String filePathToEndState = rootURLPathForLevel + "endState.json";
      JSONObject obj = (JSONObject) parser.parse(new FileReader(filePathToEndState));
    }
    catch (Exception e) {
      //figure out error handling later lol
    }
  }

  private void parsePeopleLocations(JSONObject peopleLocations)  {
    Map<Integer, List<Integer>> mapOfPeople = new HashMap<>();
    for (Object personNum: peopleLocations.keySet())  {
      JSONArray currentArray = (JSONArray) peopleLocations.get(personNum);
      List<Integer> currentList = new ArrayList<>();
      for (Object curNum: currentArray) {
        currentList.add(Integer.parseInt(curNum.toString()));
      }
      mapOfPeople.put(Integer.parseInt(personNum.toString()), currentList);
    }
  }

}
