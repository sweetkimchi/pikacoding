package ooga.model.player;

import java.util.*;
import ooga.model.Direction;

/**
 * 
 */
public class Avatar extends Player {

    private final int id;
    private Objects heldItem;

    /**
     * Default constructor
     */
    public Avatar(int id) {
        this.id = id;
        heldItem = null;
    }

    @Override
    public int getId() {
        return id;
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
    public void pickUp(Objects toPickUp) {
        heldItem = toPickUp;
    }

    /**
     * Directs the avatar to drop the block it is holding. Returns the block it dropped.
     *
     * @return The block that was dropped
     */
    public Objects drop() {
        Objects ret = heldItem;
        heldItem = null;
        return ret;
    }

}