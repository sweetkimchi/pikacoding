package ooga.view.frontendavatar;

import javafx.scene.image.ImageView;

public class FrontEndAvatar extends FrontEndSprite{

  /**
   * Constructor for FrontEndTurtle
   *
   * @param xCoord      x coordinate of the turtle
   * @param yCoord      y coordinate of the turtle
   * @param turtleImage image of the turtle
   * @param penState    state of the pen
   */
  public FrontEndAvatar(double xCoord, double yCoord, ImageView turtleImage,
      double penState) {
    super(xCoord, yCoord, turtleImage, penState);
  }
}
