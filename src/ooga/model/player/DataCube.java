package ooga.model.player;

/**
 * DataCubes are a type of block that avatars can interact with and manipulate. They contain a
 * display number that can be manipulated.
 *
 * @author Harrison Huang
 */
public class DataCube extends Block {

  private int xCoord;
  private int yCoord;
  private final int id;
  private int displayNum;

  /**
   * Constructor of the DataCube. Takes in an ID, X and Y coordinates, and a display number.
   *
   * @param id         The id of the DataCube
   * @param xCoord     The x-coordinate of the DataCube
   * @param yCoord     The y-coordinate of the DataCube
   * @param displayNum The display number of the DataCube
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
   * Getter for the x-coordinate of the Element.
   *
   * @return The x-coordinate of the Element
   */
  @Override
  public int getXCoord() {
    return xCoord;
  }

  /**
   * Getter for the y-coordinate of the Element.
   *
   * @return The y-coordinate of the Element
   */
  @Override
  public int getYCoord() {
    return yCoord;
  }

  /**
   * Updates the X and Y coordinates of the Element.
   *
   * @param xCoord The new x-coordinate
   * @param yCoord The new y-coordinate
   */
  @Override
  public void setXY(int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
  }

  /**
   * Getter for the number associated with the DataCube.
   *
   * @return The number of the DataCube
   */
  public int getDisplayNum() {
    return displayNum;
  }

  /**
   * Setter for the number associated with the DataCube.
   *
   * @param displayNum The new number of the DataCube
   */
  public void setDisplayNum(int displayNum) {
    this.displayNum = displayNum;
  }


}