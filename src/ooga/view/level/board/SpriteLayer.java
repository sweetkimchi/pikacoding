package ooga.view.level.board;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;
import ooga.model.grid.gridData.BlockData;
import ooga.view.ScreenCreator;
import ooga.view.animation.Animation;

/**
 * @author Ji Yun Hyo
 */
public class SpriteLayer extends Pane {

  private double xSize;
  private double ySize;

  private Map<Integer, ViewAvatar> avatars;
  private Map<Integer, ViewBlock> blocks;

  private Map<String, List<Integer>> initialAvatarLocations;
  private Map<String, BlockData> initialBlockData;
  private final Animation animation;
  private Map<Integer, Deque<Double>> allElementInformation;

  public SpriteLayer(double width, double height) {
    this.setId(ScreenCreator.idsForTests.getString("spriteLayer"));
    this.setMinSize(width, height);
    this.setMaxSize(width, height);
    animation = new Animation();
  }

  public void initializeAvatars(Map<String, List<Integer>> allAvatarLocations) {
    avatars = new HashMap<>();
    initialAvatarLocations = allAvatarLocations;
    initialAvatarLocations.forEach((id, location) -> {
      ViewAvatar viewAvatar = new ViewAvatar(location.get(0), location.get(1), xSize, ySize, Integer.parseInt(id), this);
      avatars.put(Integer.parseInt(id), viewAvatar);
    });
  }

  public void initializeBlocks(Map<String, BlockData> allBlockData) {
    blocks = new HashMap<>();
    initialBlockData = allBlockData;
    initialBlockData.forEach((id, blockData) -> {
      ViewBlock viewBlock = new ViewBlock(blockData.getLocation().get(0), blockData.getLocation().get(1),
          xSize,
          ySize, this, "" + blockData.getBlockNumber());
      blocks.put(Integer.parseInt(id), viewBlock);
    });
  }

  public void setSizes(double xSize, double ySize) {
    this.xSize = xSize;
    this.ySize = ySize;
  }

  /**
   * @param id
   * @param xCoord
   * @param yCoord
   */
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    ViewAvatar viewAvatar = avatars.get(id);
    animation.queuePositionUpdates(id, viewAvatar.getInitialXCoordinate(), viewAvatar.getInitialYCoordinate(), xCoord,yCoord);
  }

  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    ViewBlock viewBlock = blocks.get(id);
    animation.queuePositionUpdates(id, viewBlock.getInitialXCoordinate(), viewBlock.getInitialYCoordinate(), xCoord,yCoord);
  }

  public void updateBlock(int id, boolean isHeld) {
    blocks.get(id).setHeldStatus(isHeld);
    if(isHeld){
      blocks.get(id).setShiftHeight(1);
    }else{
      blocks.get(id).setShiftHeight(0);
    }
  }

  public void setBlockNumber(int id, int newDisplayNum) {
    blocks.get(id).updateCubeNumber(newDisplayNum);
  }

  public boolean updateAnimationForFrontEnd() {
    allElementInformation = animation.getAllElementInformation();
    boolean finished = true;
    for(Map.Entry<Integer, Deque<Double>> entry : allElementInformation.entrySet()){
      if(!entry.getValue().isEmpty()){
        double nextX = entry.getValue().pop();
        double nextY = entry.getValue().pop();
        if(avatars.containsKey(entry.getKey())){
          avatars.get(entry.getKey()).moveAvatar(nextX, nextY);
        }else if(blocks.containsKey(entry.getKey())){
          blocks.get(entry.getKey()).moveBlock(nextX, nextY);
        }
        finished = false;
      }
    }
    return finished;
  }

  public void resetAnimationQueue() {
    animation.reset();
    allElementInformation = new HashMap<>();
  }

  public void resetAvatarLocations() {
    initialAvatarLocations.forEach((id, location) -> avatars.get(Integer.parseInt(id)).reset());
  }

  public void resetBlockData() {
    initialBlockData.forEach((id, blockData) -> blocks.get(Integer.parseInt(id)).reset());
  }

  //TODO: refactor with css
  public void resetAvatarImages() {
    if(allElementInformation != null){
      for(Map.Entry<Integer, ViewAvatar> entry : avatars.entrySet()){
        avatars.get(entry.getKey()).setAvatarImage("images/PikachuAvatar.gif");
      }
    }
  }

}
