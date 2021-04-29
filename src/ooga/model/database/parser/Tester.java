package ooga.model.database.parser;

import ooga.model.database.FirebaseService;

public class Tester {

  /**
   * tester class that made sure all levels were getting parsed as expected with database and data files.
   * Needed to not run firebase tests in the test file
   * No assumptions, run main to test parser by changing playerID and level
   * @param args no args
   */
  public static void main(String[] args) {
    InitialConfigurationParser x = new InitialConfigurationParser(1, new FirebaseService(), 0);
    x.getInitialState();
  }
}
