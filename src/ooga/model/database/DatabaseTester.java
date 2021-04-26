package ooga.model.database;

import java.io.IOException;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {

    FirebaseService firebaseService = new FirebaseService();
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
    //test.checkLevelStarted();
    //PlayerInitialization test2 = new PlayerInitialization(0, 1);
//    test.checkLevelEndedForCurrentTeam();
//    test.checkLevelEndedForBothTeams();
    //firebaseService.declareEndOfGame(0, 100);
//    while (true)  {
//
//    }
  }
}
