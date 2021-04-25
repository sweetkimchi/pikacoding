package ooga.model.database;

import java.io.IOException;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {
    FirebaseService firebaseService = new FirebaseService(0, 0);
    firebaseService.saveGameLevel(6);
    //firebaseService.readDBContentsForLevelInit(1);
    //MongoDriver mongoDriver = new MongoDriver();

  }
}
