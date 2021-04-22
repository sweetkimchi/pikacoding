package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.util.Elements;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.commands.Add;
import ooga.model.commands.Drop;
import ooga.model.commands.PickUp;
import ooga.model.commands.Step;
import ooga.model.commands.Subtract;
import ooga.model.commands.Throw;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.gridData.BoardState;
import ooga.model.player.Avatar;
import ooga.model.player.AvatarData;
import ooga.model.player.DataCube;
import ooga.view.level.codearea.CommandBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private ElementInformationBundle elementInformationBundle;
  private Avatar avatar;
  private DataCube dataCube;
  private BackEndExternalAPI modelController;

  @BeforeEach
  public void setup() {
    elementInformationBundle = new ElementInformationBundle();
    modelController = new BackEndExternalAPI() {
      @Override
      public void setViewController(FrontEndExternalAPI viewController) {

      }

      @Override
      public void parseCommands(List<CommandBlock> commandBlocks) {

      }

      @Override
      public void runNextCommand() {

      }

      @Override
      public void declareEndOfAnimation() {

      }

      @Override
      public void initializeLevel(int level) {

      }

      @Override
      public void updateAvatarPositions(int id, int xCoord, int yCoord) {

      }


      @Override
      public void updateFrontEndElements(Map<String, AvatarData> updates) {

      }

      @Override
      public void setScore(int score) {

      }

      @Override
      public void setLineIndicators(Map<Integer, Integer> lineUpdates) {

      }

      @Override
      public void updateBlock(int id, boolean b) {

      }

      @Override
      public void updateBlockPositions(int id, int xCoord, int yCoord) {

      }

      @Override
      public void winLevel() {

      }

      @Override
      public void setBoardNumber(int id, int newDisplayNum) {

      }

      @Override
      public void loseLevel() {

      }
    };
    elementInformationBundle.setModelController(modelController);
    avatar = new Avatar(10, 5, 5);
    dataCube = new DataCube(0, 5, 5, 19);
    elementInformationBundle.setDimensions(10, 10);
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        elementInformationBundle.setStructure(i, j, Structure.FLOOR);
        if (i == 0 || i == 9 || j == 0 || j == 9) {
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
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);
    assertTrue(elementInformationBundle.getTileData(6, 5).hasAvatar());
    assertFalse(elementInformationBundle.getTileData(5, 5).hasAvatar());
    assertEquals(6, avatar.getXCoord());
    assertEquals(5, avatar.getYCoord());
  }

  @Test
  public void avatarAttemptsToStepIntoWall() {
    elementInformationBundle.setStructure(6, 5, Structure.WALL);
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);
    assertFalse(elementInformationBundle.getTileData(6, 5).hasAvatar());
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
  }

  @Test
  public void avatarAttemptsToStepIntoAnotherAvatar() {
    Avatar newAvatar = new Avatar(15, 6, 6);
    elementInformationBundle.addGameElement(newAvatar);
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down-right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);
    assertTrue(elementInformationBundle.getTileData(6, 6).hasAvatar());
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
  }

  @Test
  public void avatarPickUpDataCubeSameTile() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "self");
    PickUp pickUp = new PickUp(elementInformationBundle, parameters);
    pickUp.execute(10);
    assertEquals(dataCube, avatar.getHeldItem());
  }

  @Test
  public void avatarCantPickUp() {
    avatar.setXCoord(avatar.getXCoord() + 1);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "self");
    PickUp pickUp = new PickUp(elementInformationBundle, parameters);
    pickUp.execute(10);
    assertNull(avatar.getHeldItem());
  }

  @Test
  public void avatarPickUpDataCubeAndMove() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "self");
    PickUp pickUp = new PickUp(elementInformationBundle, parameters);
    pickUp.execute(10);

    parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Drop drop = new Drop(elementInformationBundle, new HashMap<>());
    drop.execute(10);
    assertEquals(dataCube.getId(), elementInformationBundle.getTileData(6, 5).getBlockId());
  }

  @Test
  public void addToDataCubeHeldByAvatar() {
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addGameElement(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Add add = new Add(elementInformationBundle, new HashMap<>());
    add.execute(10);

    assertEquals(32, dataCube.getDisplayNum());
  }

  @Test
  public void subtractFromDataCubeHeldByAvatar() {
    addToDataCubeHeldByAvatar();
    Subtract subtract = new Subtract(elementInformationBundle, new HashMap<>());
    subtract.execute(10);
    subtract.execute(10);
    assertEquals(6, dataCube.getDisplayNum());
  }

  @Test
  public void throwStraightIntoWall() {
    avatarPickUpDataCubeSameTile();
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Throw throwCommand = new Throw(elementInformationBundle, parameters);
    throwCommand.execute(10);
    assertEquals(8, dataCube.getXCoord());
    assertEquals(5, dataCube.getYCoord());
  }

  @Test
  public void throwDiagonalIntoWall() {
    avatarPickUpDataCubeSameTile();
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down-left");
    Throw throwCommand = new Throw(elementInformationBundle, parameters);
    throwCommand.execute(10);
    assertEquals(2, dataCube.getXCoord());
    assertEquals(8, dataCube.getYCoord());
  }

}
