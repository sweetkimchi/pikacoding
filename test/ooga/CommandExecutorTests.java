package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.CommandExecutor;
import ooga.model.Executor;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;
import ooga.view.level.codearea.CommandBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandExecutorTests {

  private ElementInformationBundle elementInformationBundle;
  private Avatar avatar;
  private DataCube dataCube;
  private BackEndExternalAPI modelController;
  private Executor commandExecutor;

  @BeforeEach
  public void setup(){
//    commandExecutor = new CommandExecutor();
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
      public void getTeamNumber(int teamNum) {

      }
    };
  }

  @Test
  public void checkScore(){

  }
}
