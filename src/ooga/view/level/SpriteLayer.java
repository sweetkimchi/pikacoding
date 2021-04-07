package ooga.view.level;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import ooga.model.grid.gridData.BlockData;
import ooga.view.animation.Animation;

public class SpriteLayer extends Pane {

  private double xSize;
  private double ySize;

  private Map<Integer, Avatar> avatars;
  private Map<Integer, Block> blocks;

  private Map<String, List<Integer>> initialAvatarLocations;
  private Map<String, BlockData> initialBlockData;
  private final Animation animation;

  public SpriteLayer(double width, double height) {
    this.setMinSize(width, height);
    this.setMaxSize(width, height);
    animation = new Animation();
  }

  public void setSizes(double xSize, double ySize) {
    this.xSize = xSize;
    this.ySize = ySize;
  }

  public void resetAvatarLocations() {
    initialAvatarLocations.forEach((id, location) -> {
      avatars.get(Integer.parseInt(id)).reset();
    });
  }

  public void resetBlockData() {
    initialBlockData.forEach((id, blockData) -> {
      blocks.get(Integer.parseInt(id)).reset();
    });
  }

  public void initializeAvatars(Map<String, List<Integer>> allAvatarLocations) {
    avatars = new HashMap<>();
    initialAvatarLocations = allAvatarLocations;
    initialAvatarLocations.forEach((id, location) -> {
      Avatar avatar = new Avatar(location.get(0), location.get(1), xSize, ySize, this);
      avatars.put(Integer.parseInt(id), avatar);
    });
  }

  public void initializeBlocks(Map<String, BlockData> allBlockData) {
    blocks = new HashMap<>();
    initialBlockData = allBlockData;
    initialBlockData.forEach((id, blockData) -> {
      Block block = new Block(blockData.getLocation().get(0), blockData.getLocation().get(1),
          xSize,
          ySize, this, "" + blockData.getBlockNumber());
      blocks.put(Integer.parseInt(id), block);
    });
  }

  /**
   * TODO: refactor this method once a few more commands are added
   * @param id
   * @param xCoord
   * @param yCoord
   */
  public void updateAvatarPositions(int id, int xCoord, int yCoord) {
    Avatar avatar = avatars.get(id);
    animation.queuePositionUpdates(id, avatar.getInitialXCoordinate(), avatar.getInitialYCoordinate(), xCoord,yCoord);
  //  avatars.get(id).moveAvatar(xCoord,yCoord);
  }

  public int getNumberOfAvatars() {
    return avatars.size();
  }

  public boolean updateAnimationForFrontEnd() {
    Map<Integer, Deque<Double>> allElementInformation = animation.getAllElementInformation();
    boolean finished = true;
    for(Map.Entry<Integer, Deque<Double>> entry : allElementInformation.entrySet()){
      if(!entry.getValue().isEmpty()){

        System.out.println("Moving Avatar: " + entry.getValue());
        double nextX = entry.getValue().pop();
        double nextY = entry.getValue().pop();

        avatars.get(entry.getKey()).moveAvatar(nextX, nextY);
        finished = false;
      }
    }
    return finished;
  }

  public void resetAnimationQueue() {
    animation.reset();
  }
}
