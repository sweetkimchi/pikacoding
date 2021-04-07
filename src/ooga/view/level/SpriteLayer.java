package ooga.view.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import ooga.model.grid.gridData.BlockData;

public class SpriteLayer extends Pane {

  private double xSize;
  private double ySize;

  private Map<Integer, Avatar> avatars;
  private Map<Integer, Block> blocks;

  private Map<String, List<Integer>> initialAvatarLocations;
  private Map<String, BlockData> initialBlockData;

  public SpriteLayer(double width, double height) {
    this.setMinSize(width, height);
    this.setMaxSize(width, height);
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

}
