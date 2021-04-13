package ooga.model.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.json.simple.JSONObject;


public class FirebaseService {
  private FirebaseDatabase db;
  private String rootURLPathForLevel;
  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";


  public FirebaseService() throws IOException {




    FileInputStream serviceAccount =
        new FileInputStream("data/team-three-ooga-firebase-adminsdk-fgx3y-59d0e7e80b.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://team-three-ooga-default-rtdb.firebaseio.com")
        .build();

    FirebaseApp.initializeApp(options);

    db = FirebaseDatabase.getInstance();
    System.out.println(db);
  }


  public void saveGameLevel(int level) {
    DatabaseReference ref = db.getReference("data");
    System.out.println(ref);
    DatabaseReference levelsRef = ref.child("startState/level"+level);
    this.rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + level + "/";
    String filePathToLevelInfoFile = this.rootURLPathForLevel + "level" + level + ".json";

    try {
      Map<String, Object> jsonMap = new Gson().fromJson(new FileReader(filePathToLevelInfoFile)
          , new TypeToken<HashMap<String, Object>>() {
          }.getType());
      CountDownLatch done = new CountDownLatch(1);
      FirebaseDatabase.getInstance().getReference("levels/" + "level" + level).setValue(jsonMap,
          (de, dr) -> done.countDown());
      done.await();
    }
    catch (Exception e) {

    }

  }

  public FirebaseDatabase getDb() {
    return db;
  }

}
