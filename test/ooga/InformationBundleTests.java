package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.CommandExecutor;
import ooga.model.Executor;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;
import ooga.view.level.codearea.CommandBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InformationBundleTests {

  private ElementInformationBundle elementInformationBundle;
  private Avatar avatar;
  private DataCube dataCube;
  private BackEndExternalAPI modelController;
  private Executor commandExecutor;

  @BeforeEach
  public void setup(){
//    commandExecutor = new CommandExecutor();
    elementInformationBundle = new ElementInformationBundle();
    modelController = new BackEndExternalAPI() {
      @Override
      public void setViewController(FrontEndExternalAPI viewController) {

      }

      @Override
      public void initializeLevel(int level) {

      }

      @Override
      public void parseCommands(List<CommandBlock> commandBlocks) {

      }

      @Override
      public void runNextCommand() {

      }

      @Override
      public void updateAvatarPosition(int id, int xCoord, int yCoord) {

      }

      @Override
      public void updateBlockPosition(int id, int xCoord, int yCoord) {

      }

      @Override
      public void updateBlock(int id, boolean b) {

      }

      @Override
      public void setBlockNumber(int id, int newDisplayNum) {

      }

      @Override
      public void declareEndOfRun() {

      }

      @Override
      public void setLineIndicators(Map<Integer, Integer> lineUpdates) {

      }

      @Override
      public void setScore(int score) {

      }

      @Override
      public void winLevel(int executionScore, int bonusFromNumberOfCommands,
          int bonusFromTimeTaken) {

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
      public void setTeamNumber(int teamNum) {
      }

      @Override
      public void startGameAfterBothTeamsPresent() {

      }

      @Override
      public void setMatchId(int id) {

      }
    };
  }

  @Test
  public void testSetDimensions(){
    elementInformationBundle.setDimensions(10,20);
    Tile tile = elementInformationBundle.getTile(9,19);
    Tile tile2 = elementInformationBundle.getTile(10,20);
    assertNotNull(tile);
    assertNull(tile2);
  }

  @Test
  public void testSetStructure(){
    elementInformationBundle.setDimensions(10,20);
    elementInformationBundle.setStructure(1,1, Structure.FLOOR);
    elementInformationBundle.setStructure(2,2, Structure.WALL);
    elementInformationBundle.setStructure(3,3, Structure.HOLE);
    assertEquals(Structure.FLOOR, elementInformationBundle.getStructure(1,1));
    assertEquals(Structure.WALL, elementInformationBundle.getStructure(2,2));
    assertEquals(Structure.HOLE, elementInformationBundle.getStructure(3,3));
  }

  @Test
  public void testAddBlock(){
    Block block = new DataCube(1, 2, 2, 7);
    elementInformationBundle.setDimensions(10,20);
    elementInformationBundle.setStructure(2,2, Structure.FLOOR);
    elementInformationBundle.addBlock(block);
    Block block2 = elementInformationBundle.getTile(2,2).getBlock();
    assertEquals(block, block2);
  }
}
