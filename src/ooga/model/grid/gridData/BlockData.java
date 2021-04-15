package ooga.model.grid.gridData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

public class BlockData {
  private List<Integer> location;

  private int blockNumber;

  private boolean pickedUp;

  public BlockData(List<Integer> location, int blockNumber, boolean pickedUp)  {
    this.location = location;
    this.blockNumber = blockNumber;
    this.pickedUp = pickedUp;
  }

  public BlockData(Block dataCube) {
    location = new ArrayList<>();
    location.addAll(List.of(dataCube.getXCoord(), dataCube.getYCoord()));
    blockNumber = dataCube.getDisplayNum();
    pickedUp = dataCube.isHeld();
    // TODO: do something with the datacube ID num?
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BlockData)) {
      return false;
    }
    BlockData blockData = (BlockData) obj;
    return (this.location.equals(blockData.location) &&
        this.pickedUp == blockData.pickedUp &&
        this.blockNumber == blockData.blockNumber);
  }

  public int getBlockNumber() {
    return blockNumber;
  }

  public boolean isPickedUp() {
    return pickedUp;
  }

  public List<Integer> getLocation() {
    return Collections.unmodifiableList(location);
  }
}
