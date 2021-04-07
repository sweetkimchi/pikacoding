package ooga.model;

/**
 * The Direction enum has a certain set of Directions that function to traverse spaces relative to
 * an element on the grid. It contains a delta X and a delta Y to function in this manner.
 *
 * @author Harrison Huang
 */
public enum Direction {
  SELF(0,0),
  UP(0,-1),
  DOWN(0,1),
  LEFT(-1,0),
  RIGHT(1,0),
  UP_LEFT(-1, -1),
  UP_RIGHT(1, -1),
  DOWN_LEFT(-1, 1),
  DOWN_RIGHT(1, 1);

  private final int xDel;
  private final int yDel;

  /**
   * Creates a direction using relative coordinates in the x- and y-directions.
   *
   * @param xDel The relative x-coordinate of the direction
   * @param yDel The relative y-coordinate of the direction
   */
  Direction(int xDel, int yDel) {
    this.xDel = xDel;
    this.yDel = yDel;
  }

  /**
   * Gets the relative x-coordinate of the direction.
   *
   * @return The relative x-coordinate of the direction
   */
  public int getXDel() {
    return xDel;
  }

  /**
   * Gets the relative y-coordinate of the direction.
   *
   * @return The relative y-coordinate of the direction
   */
  public int getYDel() {
    return yDel;
  }
}
