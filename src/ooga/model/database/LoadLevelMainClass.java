package ooga.model.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;

public class LoadLevelMainClass {

  private static final String ROOT_URL_FOR_CONFIG_FILES = System.getProperty("user.dir") + "/data/gameProperties/";

  public static void main(String[] args) throws IOException, InterruptedException {

    FirebaseService firebaseService = new FirebaseService();

    saveGameLevel(1, firebaseService);

    while (true)  {

    }

  }


  public static void saveGameLevel(int level, FirebaseService firebaseService) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("data");
    DatabaseReference levelsRef = ref.child("startState/level"+level);
    String rootURLPathForLevel = ROOT_URL_FOR_CONFIG_FILES + "level" + level + "/";
    String filePathToLevelInfoFile = rootURLPathForLevel + "level" + level + ".json";
    String filePathToStartState = rootURLPathForLevel + "startState.json";
    String filePathToEndState = rootURLPathForLevel + "endState.json";
    String filePathToCommands = rootURLPathForLevel + "commands.json";
    String filePathToGridState = rootURLPathForLevel + "grid.json";
    String rootDBPath = "level_info/level"+level+"/";
    firebaseService.setDatabaseContentsFromFile(rootDBPath+"levelInfo", filePathToLevelInfoFile);
    firebaseService.setDatabaseContentsFromFile(rootDBPath+"startState", filePathToStartState);
    firebaseService.setDatabaseContentsFromFile(rootDBPath+"endState", filePathToEndState);
    firebaseService.setDatabaseContentsFromFile(rootDBPath+"commands", filePathToCommands);
    firebaseService.setDatabaseContentsFromFile(rootDBPath+"grid", filePathToGridState);
  }
}
