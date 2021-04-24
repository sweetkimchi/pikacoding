package ooga.model.database;

import java.io.IOException;
import ooga.model.database.parser.CodeAreaParser;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {
    FirebaseService firebaseService = new FirebaseService(0, 0);
    //firebaseService.saveGameLevel(6);
    //firebaseService.readDBContentsForLevelInit(1);
    //MongoDriver mongoDriver = new MongoDriver();
    //firebaseService.readCodeAreaInformation(0);
    CodeAreaParser test = new CodeAreaParser();
    test.codeAreaChanged();

    while (true)  {
      int i = 0;
    }
  }
}
