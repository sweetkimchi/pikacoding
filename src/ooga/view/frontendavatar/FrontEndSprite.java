package ooga.view.frontendavatar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Ji Yun Hyo
 */
public abstract class FrontEndSprite {

  private double xCoord;
  private double yCoord;
  private double penState;
  private ImageView turtleImageView;
  private double penThickness;

  /**
   * Constructor for FrontEndTurtle
   * @param xCoord x coordinate of the turtle
   * @param yCoord y coordinate of the turtle
   * @param turtleImage image of the turtle
   * @param penState state of the pen
   */
  public FrontEndSprite(double xCoord, double yCoord, ImageView turtleImage, double penState) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.penState = penState;
    this.turtleImageView = turtleImage;
    penThickness = 1.0;
    turtleImageView.setId("Turtle");
  }

  /**
   * Sets the x coordinate of the turtle
   * @param xCoord x coordinate
   */
  public void setxCoord(double xCoord) {
    this.xCoord = xCoord;
  }

  /**
   * Sets the y coordinate of the turtle
   * @param yCoord y coordinate
   */
  public void setyCoord(double yCoord) {
    this.yCoord = yCoord;
  }

  /**
   * @return x coordinate of the turtle
   */
  public double getxCoord() {
    return xCoord;
  }

  /**
   * @return y coordinate of the turtle
   */
  public double getyCoord() {
    return yCoord;
  }

  /**
   * @return pen state
   */
  public double getPenState() {
    return penState;
  }

  /**
   * Sets the pen state
   * @param penState pen state
   */
  public void setPenState(double penState) {
    this.penState = penState;
  }

  /**
   * Sets the turtle image
   * @param image image of the turtle
   */
  public void setTurtleImageView(Image image) {
    turtleImageView.setImage(image);
  }

  /**
   * @return ImageView object of the turtle
   */
  public ImageView getTurtle() {
    return turtleImageView;
  }

  /**
   * Sets pen thickness
   * @param penThickness thickness
   */
  public void setPenThickness(double penThickness) {
    this.penThickness = penThickness;
  }

  /**
   * @return penThickness
   */
  public double getPenThickness() {
    return penThickness;
  }
}
