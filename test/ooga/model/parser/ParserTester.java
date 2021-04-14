package ooga.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import ooga.model.database.FirebaseService;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
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
    ElementInformationBundle elementInformationBundle = tester.getGameGrid();

    assertEquals(Structure.HOLE, elementInformationBundle.getStructure(4, 1));
    assertEquals(Structure.HOLE, elementInformationBundle.getStructure(2, 3));
    assertEquals(Structure.WALL, elementInformationBundle.getStructure(0, 0));
    assertEquals(elementInformationBundle.getAvatarList().size(), 3);
  }

  @Test
  public void checkParseWrongLevel(){

    InitialConfigurationParser tester = new InitialConfigurationParser(0, this.firebaseService);
    assertTrue(tester.getErrorOccurred());
  }
}
