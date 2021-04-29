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
 * Creates the layer that holds all the information concerning the avatar and block.
 * Overlays the board and is the same size of the board.
 *
 * @author Ji Yun Hyo
 * @author Kathleen Chen
 * @author David Li
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

  /**
   * Main constructor.
   * @param width width of the Board and the SpriteLayer
   * @param height height of the Board and the SpriteLayer
   */
  public SpriteLayer(double width, double height) {
    this.setId(ScreenCreator.idsForTests.getString("spriteLayer"));
    this.setMinSize(width, height);
    this.setMaxSize(width, height);
    animation = new Animation();
  }

  /**
   * Initializes each avatar (ViewAvatar) on the SpriteLayer based on its location, size, and ID.
   * @param allAvatarLocations map containing information concerning each avatar's
   *                           location, size, and id information
   */
  public void initializeAvatars(Map<String, List<Integer>> allAvatarLocations) {
    avatars = new HashMap<>();
    initialAvatarLocations = allAvatarLocations;
    initialAvatarLocations.forEach((id, location) -> {
      ViewAvatar viewAvatar = new ViewAvatar(location.get(0), location.get(1), xSize, ySize,
          Integer.parseInt(id), this);
      avatars.put(Integer.parseInt(id), viewAvatar);
    });
  }

  /**
   * Initializes each block (ViewBlock) on the SpriteLayer based on its location, size, id, and number.
   * @param allBlockData map that contains information concerning each block's
   *                     location, size, id, and number on block
   */
  public void initializeBlocks(Map<String, BlockData> allBlockData) {
    blocks = new HashMap<>();
    initialBlockData = allBlockData;
    initialBlockData.forEach((id, blockData) -> {
      ViewBlock viewBlock = new ViewBlock(blockData.getLocation().get(0),
          blockData.getLocation().get(1),
          xSize,
          ySize, this, "" + blockData.getBlockNumber());
      blocks.put(Integer.parseInt(id), viewBlock);
    });
  }

  /**
   * Sets the size of the x and y size of the objects on the SpriteLayer.
   * @param xSize x size that avatars and blocks are set to
   * @param ySize y size that avatars and blocks are set to
   */
  public void setSizes(double xSize, double ySize) {
    this.xSize = xSize;
    this.ySize = ySize;
  }

  /**
   * Update the position of an avatar with a specific id based on the new x and y coordinates.
   * @param id int that represents the id of the specific avatar that needs to be updated
   * @param xCoord int x coordinate of the new position
   * @param yCoord int y coordinate of the new position
   */
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    ViewAvatar viewAvatar = avatars.get(id);
    animation.queuePositionUpdates(id, viewAvatar.getInitialXCoordinate(),
        viewAvatar.getInitialYCoordinate(), xCoord, yCoord);
  }

  /**
   * Update the position of a particular block based on its id and new xy coordinates.
   * @param id int that represents the id of the specific block that needs to be updated
   * @param xCoord int x coordinate of the new position
   * @param yCoord int y coordinate of the new poison
   */
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    ViewBlock viewBlock = blocks.get(id);
    animation.queuePositionUpdates(id, viewBlock.getInitialXCoordinate(),
        viewBlock.getInitialYCoordinate(), xCoord, yCoord);
  }

  /**
   * Updates if the block is being held or not.
   * @param id int that represents the id of the block
   * @param isHeld boolean that represents if the block is being held by the avatar or not
   */
  public void updateBlock(int id, boolean isHeld) {
    blocks.get(id).setHeldStatus(isHeld);
    if (isHeld) {
      blocks.get(id).setShiftHeight(1);
    } else {
      blocks.get(id).setShiftHeight(0);
    }
  }

  /**
   * Changes the number on a specific block.
   * @param id int that represents the id of the block
   * @param newDisplayNum int that represents the new number to be displayed on the block
   */
  public void setBlockNumber(int id, int newDisplayNum) {
    blocks.get(id).updateCubeNumber(newDisplayNum);
  }

  /**
   * Returns a boolean of whether or not the front end animation is finished or not.
   * @return boolean finished that represents if the front end animation is finished or not
   */
  public boolean updateAnimationForFrontEnd() {
    allElementInformation = animation.getAllElementInformation();
    boolean finished = true;
    for (Map.Entry<Integer, Deque<Double>> entry : allElementInformation.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        double nextX = entry.getValue().pop();
        double nextY = entry.getValue().pop();
        if (avatars.containsKey(entry.getKey())) {
          avatars.get(entry.getKey()).moveAvatar(nextX, nextY);
        } else if (blocks.containsKey(entry.getKey())) {
          blocks.get(entry.getKey()).moveBlock(nextX, nextY);
        }
        finished = false;
      }
    }
    return finished;
  }

  /**
   * Resets the animation queue.
   */
  public void resetAnimationQueue() {
    animation.reset();
    allElementInformation = new HashMap<>();
  }

  /**
   * Resets the avatar locations and returns them to their original positions.
   */
  public void resetAvatarLocations() {
    initialAvatarLocations.forEach((id, location) -> avatars.get(Integer.parseInt(id)).reset());
  }

  /**
   * Resets the data of the block (position and number on the block).
   */
  public void resetBlockData() {
    initialBlockData.forEach((id, blockData) -> blocks.get(Integer.parseInt(id)).reset());
  }

  /**
   * Resets the avatar image to the original image.
   */
  public void resetAvatarImages() {
    if (allElementInformation != null) {
      for (Map.Entry<Integer, ViewAvatar> entry : avatars.entrySet()) {
        avatars.get(entry.getKey()).setAvatarImage("images/PikachuAvatar.gif");
      }
    }
  }

}
