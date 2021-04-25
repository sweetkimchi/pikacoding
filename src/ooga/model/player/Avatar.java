package ooga.model.player;

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

    public boolean hasBlock() {
        return heldItem != null;
    }

    public Block getHeldItem() {
        return heldItem;
    }
}