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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import ooga.model.exceptions.ExceptionHandler;
import ooga.view.level.codearea.CommandBlock;

/**
 * @author Billy Luqiu
 * @author Ji Yun Hyo
 */
public class FirebaseService {

  private final FirebaseDatabase db;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";
  public FirebaseService() {
    try{
      FileInputStream serviceAccount =
          new FileInputStream("data/firebaseKey/key.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://team-three-ooga-default-rtdb.firebaseio.com")
          .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }

      db = FirebaseDatabase.getInstance();
    }
    catch (Exception e) {
      throw new ExceptionHandler("error connecting to firebase");
    }

  }


  protected void setDatabaseContentsFromFile(String pathInDB, String pathToFile)  {
    Map<String, Object> jsonMap;
    try {
       jsonMap = new Gson().fromJson(new FileReader(pathToFile)
          , new TypeToken<HashMap<String, Object>>() {}.getType());
    }
    catch (Exception e) {
      throw new ExceptionHandler("error setting database contents");
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
      throw new ExceptionHandler("error setting database contents");
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
      throw new ExceptionHandler("error reading levels from database");
    }

  }


  private Map<String, Object> createJSONForCommandBlock(CommandBlock commandBlock) {
    Map<String, Object> map = new HashMap<>();
    map.put("index", commandBlock.getIndex());
    map.put("type", commandBlock.getType());
    map.put("parameters", commandBlock.getParameters());
    return map;
  }

  /**
   * updates the commandBlock across all
   */
  public void saveMatchInformation(int matchID, int teamID, List<CommandBlock> commandBlocks) {
    String rootDBPath = "match_info/match"+matchID+"/team" + teamID  + "/codingArea/";
    Map<String, Object> jsonMapOfCodingArea = new HashMap<>();
    for(CommandBlock commandBlock : commandBlocks){
      jsonMapOfCodingArea.put(String.valueOf(commandBlock.getIndex()), createJSONForCommandBlock(commandBlock));
    }
    setDatabaseContentsWithMap(jsonMapOfCodingArea, rootDBPath);
  }

  public void declareEndOfGame(int matchID, int teamID, int score)  {
    try{
      String rootDBPath = "match_info/match"+matchID+"/team"+teamID+"/gameEnded/";
      CountDownLatch done = new CountDownLatch(1);
      Map<String, Object> gameEnded = new HashMap<>();
      gameEnded.put("gameEnded", true);
      gameEnded.put("score", score);
      DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(rootDBPath);
      dataRef.setValue(gameEnded, (databaseError, databaseReference) -> done.countDown());
      done.await();
    }
    catch (Exception e) {
      throw new ExceptionHandler("error declaring end of game");
    }
  }

}
