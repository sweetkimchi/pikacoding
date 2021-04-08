package ooga.model.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.model.Direction;
import ooga.model.player.Avatar;
import ooga.model.player.Element;
import ooga.model.player.Objects;

/**
 * The GameGrid contains all the elements for the grid of the game.
 */
public class GameGrid implements Grid {

  private Tile[][] grid;
  private final Map<Avatar, List<Integer>> avatarList;

  public GameGrid() {
    avatarList = new HashMap<>();
  }

  public Map<Avatar, List<Integer>> getAvatarList() {
    return avatarList;
  }

  @Override
  public void setDimensions(int x, int y) {
    grid = new Tile[x][y];
    for (int i=0; i<x; i++) {
      for (int j=0; j<y; j++) {
        grid[i][j] = new Tile();
      }
    }
  }

  public Structure getStructure(int x, int y) {
    return grid[x][y].getStructure();
  }

  public void setStructure(int x, int y, Structure structure) {
    grid[x][y].setStructure(structure);
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
      avatarCoords.set(0, newX);
      avatarCoords.set(1, newY);
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


}
