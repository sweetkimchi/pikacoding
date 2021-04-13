package ooga.model.database;

import java.io.IOException;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {
    FirebaseService firebaseService = new FirebaseService();
    //firebaseService.saveGameLevel(1);
    firebaseService.readDBContentsForLevelInit(1);
    //MongoDriver mongoDriver = new MongoDriver();

  }
}
