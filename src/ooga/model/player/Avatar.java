package ooga.model.player;

import java.util.*;
import ooga.model.Direction;

/**
 * 
 */
public class Avatar extends Player {

    private final int id;

    /**
     * Default constructor
     */
    public Avatar(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Moves the avatar in a cardinal direction.
     *
     * @param direction The direction to be moved
     */
    public void step(Direction direction) {

    }

    /**
     * Directs the avatar to pick up a block from one of its specified directions.
     *
     * @param direction The direction to attempt to pick up a block
     */
    public void pickUp(Direction direction, Elements id) {

    }

    /**
     * Directs the avatar to drop the block it is holding.
     */
    public void drop(Elements id) {

    }

}