package ooga.model.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.Direction;
import ooga.model.player.Avatar;
import ooga.model.player.ElementData;
import ooga.model.player.Elements;
import ooga.model.player.Objects;

public class GameGrid implements Grid {

  private Elements[][] grid;
  private final Map<Avatar,List<Integer>> avatarList;

  public GameGrid() {
    avatarList = new HashMap<>();
  }

  @Override
  public void setDimensions(int width, int height) {
    grid = new Elements[width][height];
  }

  @Override
  public void addGameElement(Elements gameElement, int xPos, int yPos) {
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
    List<Integer> avatarCoordinates = getCoordinatesByAvatarId(avatarId);
    assert avatarCoordinates != null;
    Avatar avatar = (Avatar) grid[avatarCoordinates.get(0)][avatarCoordinates.get(1)];
    switch(direction) {
      case SELF:
        break;

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
      if (avatar.getId() == id) return avatar;
    }
    return null; // should never happen
  }

  private List<Integer> getCoordinatesByAvatarId(int id) {
    for (Avatar avatar : avatarList.keySet()) {

    }
    return null; // should never happen
  }
}
