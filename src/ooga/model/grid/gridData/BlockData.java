package ooga.model.grid.gridData;

import java.util.Collections;
import java.util.List;

public class BlockData {
  private List<Integer> location;

  private int blockNumber;

  private boolean pickedUp;

  public BlockData(List<Integer> location, int blockNumber, boolean pickedUp)  {
    this.location = location;
    this.blockNumber = blockNumber;
    this.pickedUp = pickedUp;
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
