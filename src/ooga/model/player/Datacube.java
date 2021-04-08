package ooga.model.player;

import java.util.*;

/**
 * 
 */
public class Datacube extends Objects {

    private int xCoord;
    private int yCoord;
    private final int id;
    /**
     * Default constructor
     */
    public Datacube(int id, int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.id = id;
    }

    /**
     * Getter for the ID of the element.
     *
     * @return The ID of the element
     */
    @Override
    public int getId() {
        return 0;
    }

    /**
     * @return xCoordinate of the Element
     */
    @Override
    public int getXCoord() {
        return 0;
    }

    /**
     * @return yCoordinate of the Element
     */
    @Override
    public int getYCoord() {
        return 0;
    }

    /**
     * update the xCoordinate
     */
    @Override
    public void setXCoord(int xCoord) {

    }

    /**
     * update the yCoordinate
     */
    @Override
    public void setYCoord(int yCoord) {

    }
}