package ooga.model.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.model.Direction;
import ooga.model.player.Avatar;
import ooga.model.player.ElementData;
import ooga.model.player.Element;
import ooga.model.player.Objects;
import ooga.model.player.Structure;

/**
 * The GameGrid contains all the elements for the grid of the game.
 */
public class GameGrid implements Grid {

  private Tile[][] grid;
  private final Map<Avatar, List<Integer>> avatarList;

  public GameGrid(BackEndExternalAPI modelController) {
    avatarList = new HashMap<>();
  }

  @Override
  public void setDimensions(int width, int height) {
    grid = new Tile[width][height];
    for (int i=0; i<width; i++) {
      for (int j=0; j<height; j++) {
        grid[i][j] = new Tile();
      }
    }
  }

  @Override
  public void addGameElement(Element gameElement, int xPos, int yPos) {
    grid[xPos][yPos].add(gameElement);
    if (gameElement instanceof Avatar) {
      avatarList.put((Avatar) gameElement, new ArrayList<>());
      avatarList.get(gameElement).addAll(List.of(xPos, yPos));
    }
  }

//  @Override
//  public void executeOnAvatars(Commands commands) {
//    for (Avatar avatar : avatarList.keySet()) {
//      commands.execute(avatar);
//    }
//  }

  @Override
  public List<ElementData> getChangedSprites() {
    return null;
  }

  /**
   * Moves the avatar in a cardinal direction.
   *
   * @param direction The direction to be moved
   */
  public void step(int avatarId, Direction direction) {
    Avatar avatar = getAvatarById(avatarId);
    List<Integer> avatarCoords = avatarList.get(avatar);
    assert avatarCoords != null;
    int currX = avatarCoords.get(0);
    int currY = avatarCoords.get(1);
    int newX = currX + direction.getXDel();
    int newY = currY + direction.getYDel();
    if (grid[newX][newY].canAddAvatar()) {
      grid[newX][newY].add(avatar);
      grid[currX][currY].removeAvatar();
    }

  }

  /**
   * Directs the avatar to pick up a block.
   *
   * @param direction The direction from which to pick up a block
   */
  public void pickUp(int avatarId, Direction direction) {
    Avatar avatar = getAvatarById(avatarId);
    assert avatar != null;
    List<Integer> avatarCoords = avatarList.get(avatar);
    assert avatarCoords != null;
    int currX = avatarCoords.get(0);
    int currY = avatarCoords.get(1);
    int newX = currX + direction.getXDel();
    int newY = currY + direction.getYDel();
    if (grid[newX][newY].hasBlock()) {
      avatar.pickUp(grid[newX][newY].getObject());
      grid[currX][currY].removeBlock();
    } else {
      System.out.println("There is no block to be picked up!");
    }
  }

  /**
   * Directs the avatar to drop the block it is holding.
   *
   * @param avatarId The id of the avatar
   */
  public void drop(int avatarId) {
    Avatar avatar = getAvatarById(avatarId);
    List<Integer> avatarCoords = avatarList.get(avatar);
    int currX = avatarCoords.get(0);
    int currY = avatarCoords.get(1);
    if (grid[currX][currY].canAddBlock()) {
      assert avatar != null;
      Objects block = avatar.drop();
      if (block == null) {
        System.out.println("You are not holding a block!");
      }
      grid[currX][currY].add(block);
    } else {
      System.out.println("You cannot drop here!");
    }

  }

  private Avatar getAvatarById(int id) {
    for (Avatar avatar : avatarList.keySet()) {
      if (avatar.getId() == id) {
        return avatar;
      }
    }
    return null; // should never happen
  }

  /**
   * Returns a collection of the IDs of all the current avatars.
   *
   * @return A collection of integers containing IDs
   */
  public Collection<Integer> getAvatarIds() {
    List<Integer> ids = new ArrayList<>();
    for (Avatar avatar : avatarList.keySet()) {
      ids.add(avatar.getId());
    }
    return ids;
  }

  //TODO: remove later, for testing only
  public Tile getTile(int x, int y) {
    return grid[x][y];
  }

  public Structure getStructure(int x, int y) {
    return grid[x][y].getStructure();
  }

  public void setStructure(int x, int y, Structure structure) {
    grid[x][y].setStructure(structure);
  }
}
