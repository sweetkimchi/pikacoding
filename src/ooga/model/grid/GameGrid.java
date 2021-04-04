package ooga.model.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.Direction;
import ooga.model.player.Avatar;
import ooga.model.player.ElementData;
import ooga.model.player.Element;
import ooga.model.player.Objects;

/**
 * The GameGrid contains all the elements for the grid of the game.
 */
public class GameGrid implements Grid {

  private Element[][] grid;
  private final Map<Avatar, List<Integer>> avatarList;

  public GameGrid() {
    avatarList = new HashMap<>();
  }

  @Override
  public void setDimensions(int width, int height) {
    grid = new Element[width][height];
  }

  @Override
  public void addGameElement(Element gameElement, int xPos, int yPos) {
    grid[xPos][yPos] = gameElement;
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
    if (grid[newX][newY] == null) {
      grid[newX][newY] = avatar;
      grid[currX][currY] = null;
    }

  }

  /**
   * Directs the avatar to pick up a block.
   *
   * @param direction The direction from which to pick up a block
   */
  public void pickUp(int avatarId, Direction direction) {

  }

  /**
   * Directs the avatar to drop the block it is holding.
   *
   * @param avatarId The id of the avatar
   */
  public void drop(int avatarId) {
    Avatar avatar = getAvatarById(avatarId);
    assert avatar != null;
    Objects block = avatar.drop();

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
}
