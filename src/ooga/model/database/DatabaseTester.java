package ooga.model.database;

import java.io.IOException;
import ooga.controller.ModelController;
import ooga.model.database.parser.ConcreteDatabaseListener;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {

    FirebaseService firebaseService = new FirebaseService(0, 0);
    firebaseService.saveGameLevel(10);
    //firebaseService.readDBContentsForLevelInit(1);
    //MongoDriver mongoDriver = new MongoDriver();
    //firebaseService.readCodeAreaInformation(0);
   // ConcreteDatabaseListener test = new ConcreteDatabaseListener(new ModelController(), 0, 1);
//    test.codeAreaChanged();
//
//    while (true)  {
//
//    }
  //  test.checkLevelStarted();
    //PlayerInitialization test2 = new PlayerInitialization(0, 2);

    while (true)  {

    }
  }
}
