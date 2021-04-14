package ooga.model.player;

/**
 * 
 */
public class DataCube extends Block {

    private int xCoord;
    private int yCoord;
    private final int id;
    private int displayNum;
    /**
     * Default constructor
     */
    public DataCube(int id, int xCoord, int yCoord, int displayNum) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.id = id;
        this.setDisplayNum(displayNum);
    }

    /**
     * Getter for the ID of the element.
     *
     * @return The ID of the element
     */
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

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }
}