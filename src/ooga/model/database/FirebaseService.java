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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FirebaseService {

  private FirebaseDatabase db;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";

  public FirebaseService() {

    try{
      FileInputStream serviceAccount =
          new FileInputStream("data/team-three-ooga-firebase-adminsdk-fgx3y-59d0e7e80b.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://team-three-ooga-default-rtdb.firebaseio.com")
          .build();

      FirebaseApp.initializeApp(options);

      db = FirebaseDatabase.getInstance();
    }
    catch (Exception e) {
      e.printStackTrace();
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
    setDatabaseContents(rootDBPath+"levelInfo", filePathToLevelInfoFile);
    setDatabaseContents(rootDBPath+"startState", filePathToStartState);
    setDatabaseContents(rootDBPath+"endState", filePathToEndState);
    setDatabaseContents(rootDBPath+"commands", filePathToCommands);
    setDatabaseContents(rootDBPath+"grid", filePathToGridState);


  }

  private void setDatabaseContents(String pathInDB, String pathToFile)  {
    try {
      Map<String, Object> jsonMap = new Gson().fromJson(new FileReader(pathToFile)
          , new TypeToken<HashMap<String, Object>>() {}.getType());
      CountDownLatch done = new CountDownLatch(1);
      //Database Error is de, database reference is dr
      //https://stackoverflow.com/questions/49723347/how-to-save-data-to-firebase-using-java-desktop
      FirebaseDatabase.getInstance().getReference(pathInDB).setValue(jsonMap,
          (de, dr) -> done.countDown());
      done.await();
    }
    catch (Exception e) {

    }
  }

  public String readDBContentsForLevelInit(int level) throws InterruptedException {
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference("level_info/level"+level+"/");
    CountDownLatch done = new CountDownLatch(1);

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



  public FirebaseDatabase getDb() {
    return db;
  }

}
