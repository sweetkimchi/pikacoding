package ooga.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
import org.junit.jupiter.api.Test;

public class ParserTester {


  @Test
  public void checkParseLevel1() {
    InitialConfigurationParser tester = new InitialConfigurationParser(1);
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
    InitialConfigurationParser tester = new InitialConfigurationParser(1);
    GameGrid gameGrid = tester.getGameGrid();

    assertEquals(Structure.HOLE, gameGrid.getStructure(4, 1));
    assertEquals(Structure.HOLE, gameGrid.getStructure(2, 3));
    assertEquals(Structure.WALL, gameGrid.getStructure(0, 0));
    assertEquals(gameGrid.getAvatarList().size(), 3);
  }

  @Test
  public void checkParseWrongLevel(){
    InitialConfigurationParser tester = new InitialConfigurationParser(0);
    assertTrue(tester.getErrorOccurred());
  }
}
