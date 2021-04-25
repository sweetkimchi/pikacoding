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
import ooga.model.commands.Decrement;
import ooga.model.commands.Drop;
import ooga.model.commands.Endif;
import ooga.model.commands.If;
import ooga.model.commands.Increment;
import ooga.model.commands.Jump;
import ooga.model.commands.Multiply;
import ooga.model.commands.Nearest;
import ooga.model.commands.PickUp;
import ooga.model.commands.SetZero;
import ooga.model.commands.Step;
import ooga.model.commands.Subtract;
import ooga.model.commands.Tell;
import ooga.model.commands.Throw;
import ooga.model.commands.ThrowOver;
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
      public void declareEndOfRun() {

      }

      @Override
      public void initializeLevel(int level) {

      }

      @Override
      public void updateAvatarPosition(int id, int xCoord, int yCoord) {

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
      public void updateBlockPosition(int id, int xCoord, int yCoord) {

      }

      @Override
      public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {

      }

      @Override
      public void setBlockNumber(int id, int newDisplayNum) {

      }

      @Override
      public void loseLevel() {

      }

      @Override
      public void updateProgram(List<CommandBlock> program) {

      }

      @Override
      public void checkTimeLeftOrNot() {

      }

      @Override
      public void timedOut() {

      }

      @Override
      public void updateTime(int timeLeft) {

      }

      @Override
      public void receivedProgramUpdate(List<CommandBlock> program) {

      }

      @Override
      public void getTeamNumber(int teamNum) {

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
    elementInformationBundle.addAvatar(avatar);
    elementInformationBundle.addBlock(dataCube);
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
    elementInformationBundle.addAvatar(newAvatar);
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
    elementInformationBundle.addBlock(dataCube1);
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

  @Test
  public void testNearest(){
    Map<String, String> parameters = new HashMap<>();
    parameters.put("target", "datacube");
    Nearest nearest = new Nearest(elementInformationBundle, parameters);
    nearest.execute(10);
    assertEquals(5, avatar.getXCoord());
  }

  @Test
  public void testMultiply(){
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addBlock(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Multiply add = new Multiply(elementInformationBundle, new HashMap<>());
    add.execute(10);

    assertEquals(247, dataCube.getDisplayNum());
  }

  @Test
  public void testDecrement(){
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addBlock(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Decrement add = new Decrement(elementInformationBundle, new HashMap<>());
    add.execute(10);

    assertEquals(18, dataCube.getDisplayNum());
  }

  @Test
  public void testJump(){
    int initialPC = avatar.getProgramCounter();
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addBlock(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("destination", "4");

    Jump jump = new Jump(elementInformationBundle, parameters);
    jump.execute(10);

    int finalPC = avatar.getProgramCounter();

    assertEquals(finalPC, 4);

  }

  @Test
  public void testIncrement(){
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addBlock(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Increment add = new Increment(elementInformationBundle, new HashMap<>());
    add.execute(10);

    assertEquals(20, dataCube.getDisplayNum());
  }

  @Test
  public void testSetZero(){
    DataCube dataCube1 = new DataCube(15, 5, 6, 13);
    elementInformationBundle.addBlock(dataCube1);
    avatarPickUpDataCubeSameTile();

    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "down");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    SetZero add = new SetZero(elementInformationBundle, new HashMap<>());
    add.execute(10);

    assertEquals(0, dataCube.getDisplayNum());
  }

  @Test
  public void testIf(){
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    If ifCommand = new If(elementInformationBundle, parameters);
    parameters.put("comparator", "equals");
    parameters.put("target", "wall");
    ifCommand.execute(10);

    assertTrue(elementInformationBundle.getTileData(6, 5).hasAvatar());
    assertFalse(elementInformationBundle.getTileData(5, 5).hasAvatar());
    assertEquals(6, avatar.getXCoord());
    assertEquals(5, avatar.getYCoord());
  }

  @Test
  public void testThrowOver(){
    avatarPickUpDataCubeSameTile();
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    ThrowOver throwCommand = new ThrowOver(elementInformationBundle, parameters);
    throwCommand.execute(10);
    assertEquals(7, dataCube.getXCoord());
    assertEquals(5, dataCube.getYCoord());
  }

  @Test
  public void testTell(){
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Tell tell = new Tell(elementInformationBundle, parameters);
    parameters.put("id", "10");
    tell.execute(10);

    assertEquals(3, avatar.getProgramCounter());
  }

  @Test
  public void testEndif(){
    assertTrue(elementInformationBundle.getTileData(5, 5).hasAvatar());
    Map<String, String> parameters = new HashMap<>();
    parameters.put("direction", "right");
    Step step = new Step(elementInformationBundle, parameters);
    step.execute(10);

    Endif endif = new Endif(elementInformationBundle, parameters);
    parameters.put("id", "10");
    endif.execute(10);

    assertEquals(3, avatar.getProgramCounter());
  }

}
