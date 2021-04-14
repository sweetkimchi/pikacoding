package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.model.Direction;
import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private GameGrid gameGrid;
  private Avatar avatar;
  private DataCube dataCube;

  @BeforeEach
  public void setup() {
    gameGrid = new GameGrid();
    avatar = new Avatar(10, 5, 5);
    dataCube = new DataCube(0, 5, 5, 0);
    gameGrid.setDimensions(10, 10);
    for (int i=0; i<10; i++) {
      for (int j=0; j<10; j++) {
        gameGrid.setStructure(i, j, Structure.FLOOR);
        if (i==0 || i==9 || j==0 || j==9) {
          gameGrid.setStructure(i, j, Structure.WALL);
        }
      }
    }
    gameGrid.addGameElement(avatar);
    gameGrid.addGameElement(dataCube);
  }

  @Test
  public void getAvatarIdsHasCurrentAvatar() {
    assertTrue(gameGrid.getAvatarIds().contains(10));
  }

  @Test
  public void setUpStructuresCorrectly() {
    assertEquals(Structure.FLOOR, gameGrid.getStructure(5, 3));
    assertEquals(Structure.WALL, gameGrid.getStructure(0, 3));
  }

  @Test
  public void avatarStepRight() {
    assertTrue(gameGrid.getTile(5,5).hasAvatar());
    gameGrid.step(10, Direction.RIGHT);
    assertTrue(gameGrid.getTile(6,5).hasAvatar());
    assertFalse(gameGrid.getTile(5,5).hasAvatar());
  }

  @Test
  public void avatarAttemptsToStepIntoWall() {
    gameGrid.setStructure(6, 5, Structure.WALL);
    assertTrue(gameGrid.getTile(5,5).hasAvatar());
    gameGrid.step(10, Direction.RIGHT);
    assertFalse(gameGrid.getTile(6,5).hasAvatar());
    assertTrue(gameGrid.getTile(5,5).hasAvatar());
  }

  @Test
  public void avatarAttemptsToStepIntoAnotherAvatar() {
    Avatar newAvatar = new Avatar(15, 6, 6);
    gameGrid.addGameElement(newAvatar);
    assertTrue(gameGrid.getTile(5,5).hasAvatar());
    gameGrid.step(10, Direction.DOWN_RIGHT);
    assertTrue(gameGrid.getTile(6,6).hasAvatar());
    assertTrue(gameGrid.getTile(5,5).hasAvatar());
  }

  @Test
  public void avatarPickUpDataCubeSameTile() {
    gameGrid.pickUp(10, Direction.SELF);
    assertEquals(dataCube, avatar.drop());
  }

  @Test
  public void avatarCantPickUp() {
    gameGrid.pickUp(10, Direction.UP);
    assertNull(avatar.drop());
  }

  @Test
  public void avatarPickUpDataCubeAndMove() {
    gameGrid.pickUp(10, Direction.SELF);
    gameGrid.step(10, Direction.RIGHT);
    gameGrid.drop(10);
    assertEquals(dataCube.getId(), gameGrid.getTile(6, 5).getBlockId());
  }

}
