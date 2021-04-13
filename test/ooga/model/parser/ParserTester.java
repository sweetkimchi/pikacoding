package ooga.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import ooga.model.database.FirebaseService;
import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserTester {

  private FirebaseService firebaseService = new FirebaseService();

  @Test
  public void checkParseLevel1() {
    InitialConfigurationParser tester = new InitialConfigurationParser(1, firebaseService);
    GoalState goalState = tester.getGoalState();
    InitialState initialState = tester.getInitialState();
    System.out.println(tester.getErrorMessage());
    assertEquals(1, initialState.getLevel());
    assertEquals(3, initialState.getNumPeople());
    assertEquals(Arrays.asList("step", "pickUp", "drop"), initialState.getCommandsAvailable());
    assertNotNull(goalState.getAllAvatarLocations());
    assertNotNull(goalState.getAllAvatarLocations().get("7"));
    assertFalse(tester.getErrorOccurred());
  }

  @Test
  public void checkGameGridParseLevel1()  {

    InitialConfigurationParser tester = new InitialConfigurationParser(1, this.firebaseService);
    GameGrid gameGrid = tester.getGameGrid();

    assertEquals(Structure.HOLE, gameGrid.getStructure(4, 1));
    assertEquals(Structure.HOLE, gameGrid.getStructure(2, 3));
    assertEquals(Structure.WALL, gameGrid.getStructure(0, 0));
    assertEquals(gameGrid.getAvatarList().size(), 3);
  }

  @Test
  public void checkParseWrongLevel(){

    InitialConfigurationParser tester = new InitialConfigurationParser(0, this.firebaseService);
    assertTrue(tester.getErrorOccurred());
  }
}
