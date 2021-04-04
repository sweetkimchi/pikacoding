package ooga.model.grid;

import java.util.ArrayList;
import java.util.List;
import ooga.model.commands.Commands;
import ooga.model.player.Avatar;
import ooga.model.player.ElementData;
import ooga.model.player.Elements;

public class GameGrid implements Grid {

  private Elements[][] grid;
  private final List<Avatar> avatarList;

  public GameGrid() {
    avatarList = new ArrayList<>();
  }

  @Override
  public void setDimensions(int width, int height) {
    grid = new Elements[width][height];
  }

  @Override
  public void addGameElement(Elements gameElement, int xPos, int yPos) {
    grid[xPos][yPos] = gameElement;
    if (gameElement instanceof Avatar) {
      avatarList.add((Avatar) gameElement);
    }
  }

  @Override
  public void executeOnAvatars(Commands commands) {
    for (Avatar avatar : avatarList) {
      commands.execute(avatar);
    }
  }

  @Override
  public List<ElementData> getChangedSprites() {
    return null;
  }
}
