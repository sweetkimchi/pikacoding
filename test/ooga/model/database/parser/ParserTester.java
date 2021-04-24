package ooga.model.database.parser;

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
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserTester {

  private static FirebaseService firebaseService;
  @BeforeAll
  public static void init() {
    firebaseService = new FirebaseService(0, 0);
  }

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

  @Test
  public void checkEndStateCorrect()  {
    InitialConfigurationParser tester = new InitialConfigurationParser(1, this.firebaseService);
    GoalState parsedGoalState = tester.getGoalState();

    ElementInformationBundle grid = new ElementInformationBundle();
    grid.setDimensions(12, 8);
    grid.setStructure(1, 4, Structure.FLOOR);
    grid.setStructure(4, 4, Structure.FLOOR);
    grid.setStructure(6, 4, Structure.FLOOR);
    grid.addAvatar(new Avatar(7, 1, 4));
    grid.addAvatar(new Avatar(8, 4, 4));
    grid.addAvatar(new Avatar(9, 6, 4));
    DataCube four = new DataCube(4, 1, 4, 4);
    DataCube five = new DataCube(5, 4, 4, 7);
    DataCube six = new DataCube(6, 6, 4, 7);
    four.pickUp(7);
    five.pickUp(8);
    six.pickUp(9);

    grid.addBlock(four);
    grid.addBlock(five);
    grid.addBlock(six);

    assertTrue(parsedGoalState.checkGameEnded(grid));
  }

  @Test
  public void checkUploadWrongLevel(){
    firebaseService.saveGameLevel(-1);
    assertTrue(firebaseService.getExceptionOccured());
  }

  @Test
  public void checkUploadLevel(){
    firebaseService.saveGameLevel(1);
    assertFalse(firebaseService.getExceptionOccured());
  }
}
