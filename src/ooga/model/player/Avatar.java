package ooga.model.player;

import ooga.model.Direction;

/**
 * 
 */
public class Avatar extends Player {

    private final int id;
    private Block heldItem;
    private int xCoord;
    private int yCoord;

    /**
     * Default constructor
     */
    public Avatar(int id, int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.id = id;
        heldItem = null;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return xCoordinate of the Element
     */
    @Override
    public int getXCoord() {
        return xCoord;
    }

    /**
     * @return yCoordinate of the Element
     */
    @Override
    public int getYCoord() {
        return yCoord;
    }

    /**
     * update the xCoordinate
     */
    @Override
    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * update the yCoordinate
     */
    @Override
    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

//    /**
//     * Moves the avatar in a cardinal direction.
//     *
//     * @param direction The direction to be moved
//     */
//    public void step(Direction direction) {
//
//    }

    /**
     * Directs the avatar to pick up a block.
     *
     * @param toPickUp The block to pick up
     */
    public void pickUp(Block toPickUp) {
        heldItem = toPickUp;
    }

    /**
     * Directs the avatar to drop the block it is holding. Returns the block it dropped.
     *
     * @return The block that was dropped
     */
    public Block drop() {
        Block ret = heldItem;
        heldItem = null;
        return ret;
    }

    public void step(Direction direction){
        int xPrev = xCoord;
        int yPrev = yCoord;
        this.xCoord = xPrev + direction.getXDel();
        this.yCoord = yPrev + direction.getYDel();

    //    System.out.printf("Moving avatar %d from (%d, %d) in the direction %s to new location (%d, %d)\n", id, xPrev, yPrev, direction, xCoord, yCoord);
    }



}