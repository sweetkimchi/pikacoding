package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import ooga.controller.ModelController;
import ooga.controller.ViewController;
import ooga.model.Direction;
import ooga.model.commands.Step;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private ElementInformationBundle elementInformationBundle;
  private Avatar avatar;
  private DataCube dataCube;
  private ModelController modelController;
  private ViewController viewController;

  @BeforeEach
  public void setup() {
    elementInformationBundle = new ElementInformationBundle();
//    modelController = new ModelController();
//    viewController = new ViewController(new Stage());
//    modelController.setViewController(viewController);
//    elementInformationBundle.setModelController(modelController);
    avatar = new Avatar(10, 5, 5);
    dataCube = new DataCube(0, 5, 5, 0);
    elementInformationBundle.setDimensions(10, 10);
    for (int i=0; i<10; i++) {
      for (int j=0; j<10; j++) {
        elementInformationBundle.setStructure(i, j, Structure.FLOOR);
        if (i==0 || i==9 || j==0 || j==9) {
          elementInformationBundle.setStructure(i, j, Structure.WALL);
        }
      }
    }
    elementInformationBundle.addGameElement(avatar);
    elementInformationBundle.addGameElement(dataCube);
  }

  @Test
  public void getAvatarIdsHasCurrentAvatar() {
    assertTrue(elementInformationBundle.getAvatarIds().contains(10));
  }

  @Test
  public void setUpStructuresCorrectly() {
    assertEquals(Structure.FLOOR, elementInformationBundle.getStructure(5, 3));
    assertEquals(Structure.WALL, elementInformationBundle.getStructure(0, 3));
  }

  @Test
  public void avatarStepRight() {
    assertTrue(elementInformationBundle.getTileData(5,5).hasAvatar());
//    Map<String,String> parameters = new HashMap<>();
//    parameters.put("direction","right");
//    Step step = new Step(elementInformationBundle, parameters);
//    step.execute(10);
    elementInformationBundle.step(10, Direction.RIGHT);
    assertTrue(elementInformationBundle.getTileData(6,5).hasAvatar());
    assertFalse(elementInformationBundle.getTileData(5,5).hasAvatar());
  }

  @Test
  public void avatarAttemptsToStepIntoWall() {
    elementInformationBundle.setStructure(6, 5, Structure.WALL);
    assertTrue(elementInformationBundle.getTileData(5,5).hasAvatar());
    elementInformationBundle.step(10, Direction.RIGHT);
    assertFalse(elementInformationBundle.getTileData(6,5).hasAvatar());
    assertTrue(elementInformationBundle.getTileData(5,5).hasAvatar());
  }

  @Test
  public void avatarAttemptsToStepIntoAnotherAvatar() {
    Avatar newAvatar = new Avatar(15, 6, 6);
    elementInformationBundle.addGameElement(newAvatar);
    assertTrue(elementInformationBundle.getTileData(5,5).hasAvatar());
    elementInformationBundle.step(10, Direction.DOWN_RIGHT);
    assertTrue(elementInformationBundle.getTileData(6,6).hasAvatar());
    assertTrue(elementInformationBundle.getTileData(5,5).hasAvatar());
  }

  @Test
  public void avatarPickUpDataCubeSameTile() {
    elementInformationBundle.pickUp(10, Direction.SELF);
    assertEquals(dataCube, avatar.drop());
  }

  @Test
  public void avatarCantPickUp() {
    elementInformationBundle.pickUp(10, Direction.UP);
    assertNull(avatar.drop());
  }

  @Test
  public void avatarPickUpDataCubeAndMove() {
    elementInformationBundle.pickUp(10, Direction.SELF);
    elementInformationBundle.step(10, Direction.RIGHT);
    elementInformationBundle.drop(10);
    assertEquals(dataCube.getId(), elementInformationBundle.getTileData(6, 5).getBlockId());
  }

}
