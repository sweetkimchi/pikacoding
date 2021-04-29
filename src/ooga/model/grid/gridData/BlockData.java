package ooga.model.grid.gridData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ooga.model.player.Block;

/**
 * Class that encapsulates data regarding a block.
 *
 * Depends on the block class to generate data class
 * No assumptions.
 */
public class BlockData {

  private final List<Integer> location;

  private final int blockNumber;

  private final boolean pickedUp;

  private int id;

  /**
   * Constructor that generates block data from parsed data infromation
   * @param location block in x/y coordinate
   * @param blockNumber number to dipslay on block
   * @param pickedUp true if true or not
   * @param id id of block
   */
  public BlockData(List<Integer> location, int blockNumber, boolean pickedUp, int id) {
    this.location = location;
    this.blockNumber = blockNumber;
    this.pickedUp = pickedUp;
  }

  /**
   * Constructor that generates block data from datacube object
   * @param dataCube nonnull datacube object
   */
  public BlockData(Block dataCube) {
    location = new ArrayList<>();
    location.addAll(List.of(dataCube.getXCoord(), dataCube.getYCoord()));
    blockNumber = dataCube.getDisplayNum();
    pickedUp = dataCube.isHeld();
    id = dataCube.getId();
    // TODO: do something with the datacube ID num?
  }

  /**
   * returns equal if two blocks are the same.
   * @param obj thing to compare to
   * @return if obj is a blockData, and all instance variables are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BlockData blockData)) {
      return false;
    }

    return (this.location.equals(blockData.location) &&
        this.pickedUp == blockData.pickedUp &&
        this.blockNumber == blockData.blockNumber);
  }

  /**
   * getter method for id.
   * @return id of block
   */
  public int getId() {
    return this.id;
  }

  /**
   * getter method for block number.
   * @return block number
   */
  public int getBlockNumber() {
    return blockNumber;
  }

  /**
   * getter method for if block has been picked up by Pikachu or not
   * @return true if block has been picked up, false otherwise.
   */
  public boolean isPickedUp() {
    return pickedUp;
  }


  /**
   * returns location fo block
   * @return List representing <X,Y> of block
   */
  public List<Integer> getLocation() {
    return Collections.unmodifiableList(location);
  }
}
