package ooga.model.database;

import java.io.IOException;
import ooga.controller.ModelController;
import ooga.model.database.parser.CodeAreaParser;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {

    //FirebaseService firebaseService = new FirebaseService(0, 0);
    //firebaseService.saveGameLevel(6);
    //firebaseService.readDBContentsForLevelInit(1);
    //MongoDriver mongoDriver = new MongoDriver();
    //firebaseService.readCodeAreaInformation(0);
    CodeAreaParser test = new CodeAreaParser(new ModelController(), 0, 0);
    test.codeAreaChanged();

    while (true)  {

    }
  }
}
