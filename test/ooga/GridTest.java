package ooga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.Direction;
import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.player.Avatar;
import ooga.model.player.Datacube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private GameGrid gameGrid;
  private Avatar avatar;
  private Datacube datacube;
  private BackEndExternalAPI modelController;

  @BeforeEach
  public void setup() {
    modelController = new ModelController();
    gameGrid = new GameGrid(modelController);
    avatar = new Avatar(10, 0, 0);
    datacube = new Datacube();
    gameGrid.setDimensions(10, 10);
    for (int i=0; i<10; i++) {
      for (int j=0; j<10; j++) {
        gameGrid.setStructure(i, j, Structure.FLOOR);
        if (i==0 || i==9 || j==0 || j==9) {
          gameGrid.setStructure(i, j, Structure.WALL);
        }
      }
    }
    gameGrid.addGameElement(avatar, 5, 5);
    gameGrid.addGameElement(datacube, 5, 5);
  }

  @Test
  public void getAvatarIds() {
    assertTrue(gameGrid.getAvatarIds().contains(10));
  }

  @Test
  public void setUpStructures() {
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
  public void avatarCantPickUp() {
    gameGrid.pickUp(10, Direction.UP);
    assertNull(avatar.drop());
  }

  @Test
  public void avatarPickUpDatacubeSameTile() {
    gameGrid.pickUp(10, Direction.SELF);
    assertEquals(datacube, avatar.drop());
  }

  @Test
  public void avatarPickUpDatacubeAndMove() {
    gameGrid.pickUp(10, Direction.SELF);
    gameGrid.step(10, Direction.RIGHT);
    gameGrid.drop(10);
    assertEquals(datacube, gameGrid.getTile(6, 5).getObject());
  }

}
