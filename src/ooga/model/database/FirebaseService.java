package ooga.model.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import ooga.view.level.codearea.CommandBlock;

/**
 * @author Billy Luqiu
 * @author Ji Yun Hyo
 */
public class FirebaseService {

  private FirebaseDatabase db;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";
  private boolean exceptionOccured = false;
  private int teamID;
  private int playerID;
  public FirebaseService(int teamID, int playerID) {
    this.playerID = playerID;
    this.teamID = teamID;
    try{
      FileInputStream serviceAccount =
          new FileInputStream("data/firebaseKey/key.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://team-three-ooga-default-rtdb.firebaseio.com")
          .build();

      FirebaseApp.initializeApp(options);

      db = FirebaseDatabase.getInstance();
    }
    catch (Exception e) {
      e.printStackTrace();
      exceptionOccured = true;
    }

  }


  //TODO: Remap with property files instead of hard coded values

  public void saveGameLevel(int level) {
    DatabaseReference ref = db.getReference("data");
    DatabaseReference levelsRef = ref.child("startState/level"+level);
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + level + "/";
    String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + level + ".json";
    String filePathToStartState = rootURLPathForLevel + "startState.json";
    String filePathToEndState = rootURLPathForLevel + "endState.json";
    String filePathToCommands = rootURLPathForLevel + "commands.json";
    String filePathToGridState = rootURLPathForLevel + "grid.json";
    String rootDBPath = "level_info/level"+level+"/";
    setDatabaseContentsFromFile(rootDBPath+"levelInfo", filePathToLevelInfoFile);
    setDatabaseContentsFromFile(rootDBPath+"startState", filePathToStartState);
    setDatabaseContentsFromFile(rootDBPath+"endState", filePathToEndState);
    setDatabaseContentsFromFile(rootDBPath+"commands", filePathToCommands);
    setDatabaseContentsFromFile(rootDBPath+"grid", filePathToGridState);
  }


  private void setDatabaseContentsFromFile(String pathInDB, String pathToFile)  {
    Map<String, Object> jsonMap = null;
    try {
       jsonMap = new Gson().fromJson(new FileReader(pathToFile)
          , new TypeToken<HashMap<String, Object>>() {}.getType());
    }
    catch (Exception e) {
      exceptionOccured = true;
      return;
    }
    setDatabaseContentsWithMap(jsonMap, pathInDB);
  }

  private void setDatabaseContentsWithMap(Map<String, Object> jsonMap, String pathInDB)  {

    try{
      CountDownLatch done = new CountDownLatch(1);
      //Database Error is de, database reference is dr
      //https://stackoverflow.com/questions/49723347/how-to-save-data-to-firebase-using-java-desktop
      FirebaseDatabase.getInstance().getReference(pathInDB).setValue(jsonMap,
          (de, dr) -> done.countDown());
      done.await();
    }
    catch (Exception e) {
      exceptionOccured = true;
    }
  }

  public String readDBContentsForLevelInit(int level) {
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference("level_info/level"+level+"/");
    CountDownLatch done = new CountDownLatch(1);
    try {
      final String[] json = {""};
      ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          done.countDown();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          // Code
        }
      });
      done.await();
      return json[0];
    }
    catch (Exception e) {
      exceptionOccured = true;
      return null;
    }

  }

  public void setCurrentState(String userName, List<CommandBlock> commandBlocks)  {
    List<Map<String, Object>> listOfCommands = new ArrayList<>();
    commandBlocks.forEach(commandBlock -> listOfCommands.add(createJSONForCommandBlock(commandBlock)));
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put(userName, listOfCommands);
    setDatabaseContentsWithMap(jsonMap, "gameState/");
  }

  private Map<String, Object> createJSONForCommandBlock(CommandBlock commandBlock) {
    Map<String, Object> map = new HashMap<>();
    map.put("index", commandBlock.getIndex());
    map.put("type", commandBlock.getType());
    map.put("parameters", commandBlock.getParameters());
    return map;
  }

  public boolean getExceptionOccured()  {
    return this.exceptionOccured;
  }

  /**
   * updates the commandBlock across all
   */
  public void saveMatchInformation(int matchID, List<CommandBlock> commandBlocks) {
    //TESTING CODE
    matchID = 0;

    String rootDBPath = "match_info/match"+matchID+"/team" + this.teamID + "/";
    Map<String, Object> jsonMapOfCodingArea = new HashMap<>();
    for(CommandBlock commandBlock : commandBlocks){
      jsonMapOfCodingArea.put(String.valueOf(commandBlock.getIndex()), createJSONForCommandBlock(commandBlock));
    }
    Map<String,Object> jsonMap = new HashMap<>();
    jsonMap.put("codingArea", jsonMapOfCodingArea);
    setDatabaseContentsWithMap(jsonMap, rootDBPath);
  }

}
