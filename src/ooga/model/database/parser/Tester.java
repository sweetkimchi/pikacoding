package ooga.model.database.parser;

import ooga.model.database.FirebaseService;

public class Tester {


  public static void main(String[] args) {
    InitialConfigurationParser x = new InitialConfigurationParser(1, new FirebaseService(), 0);
    x.getInitialState();
  }
}
