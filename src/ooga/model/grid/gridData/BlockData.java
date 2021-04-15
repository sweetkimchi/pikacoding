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

  private int id;

  public BlockData(List<Integer> location, int blockNumber, boolean pickedUp, int id)  {
    this.location = location;
    this.blockNumber = blockNumber;
    this.pickedUp = pickedUp;
  }

  public BlockData(Block dataCube) {
    location = new ArrayList<>();
    location.addAll(List.of(dataCube.getXCoord(), dataCube.getYCoord()));
    blockNumber = dataCube.getDisplayNum();
    pickedUp = dataCube.isHeld();
    id = dataCube.getId();
    // TODO: do something with the datacube ID num?
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BlockData)) {
      return false;
    }
    BlockData blockData = (BlockData) obj;
    System.out.println("GOAL STATE BLOCK loc" + this.getLocation() + " pickedUP" + this.pickedUp + "block num" + this.blockNumber);
    System.out.println("CURRENT STATE BLOCK loc" + blockData.getLocation() + " pickedUP" + blockData.pickedUp + "block num" + blockData.blockNumber);

    return (this.location.equals(blockData.location) &&
        this.pickedUp == blockData.pickedUp &&
        this.blockNumber == blockData.blockNumber);
  }

  public int getId()  { return this.id; }

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
