package ooga.model.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import ooga.model.grid.gridData.GoalState;
import ooga.model.grid.gridData.InitialState;
import org.junit.jupiter.api.Test;

public class ParserTester {


  @Test
  public void checkParseLevel1() {
    InitialConfigurationParser tester = new InitialConfigurationParser(1);
    GoalState goalState = tester.getGoalState();
    InitialState initialState = tester.getInitialState();

    assertEquals(1, initialState.getLevel());
    assertEquals(3, initialState.getNumPeople());
    assertEquals(Arrays.asList("step", "pickUp", "drop"), initialState.getCommandsAvailable());
    assertNotNull(initialState.getImageLocations());
    assertNotNull(goalState.getAllAvatarLocations());
    assertNotNull(goalState.getAllAvatarLocations().get("1"));
  }
}
