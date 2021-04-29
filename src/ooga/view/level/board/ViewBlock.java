package ooga.view.level.board;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Creates each block that is displayed on the board.
 * Contains the methods to move/update created block.
 *
 * @author Kathleen Chen
 * @author David Li
 */
public class ViewBlock extends StackPane {

  private static final double PADDING_RATIO = 0.25;
  private static final double PICKEDUP_SHIFT = 0.5;

  private final int initialXCoordinate;
  private final int initialYCoordinate;
  private final double width;
  private final double height;
  private final double padding;
  private Text blockText;
  private final SpriteLayer spriteLayer;
  private final String number;
  private double currentX;
  private double currentY;
  private Rectangle block;
  private boolean isHeld;

  /**
   * Main constructor.
   * @param x x coordinate int value
   * @param y y coordinate int value
   * @param w width double value
   * @param h height double value
   * @param root SpriteLayer which the avatar will be displayed on
   * @param num String number to be displayed on block
   */
  public ViewBlock(int x, int y, double w, double h, SpriteLayer root, String num) {
    initialXCoordinate = x;
    initialYCoordinate = y;
    isHeld = false;
    width = w;
    height = h;
    padding = PADDING_RATIO * width;
    spriteLayer = root;
    number = num;
    currentX = initialXCoordinate;
    currentY = initialYCoordinate;
    makeBlock();
  }

  /**
   * Moves the avatar based on the x and y distances.
   * Contains information on what to do if the block is picked up or not
   * @param x double distance that the avatar will move in the x direction
   * @param y double distance that the avatar will move in the y direction
   */
  public void moveBlock(double x, double y) {
    this.setTranslateX(x * width + padding);
    currentX = x;
    double shift = 0;
    if (isHeld) {
      shift = PICKEDUP_SHIFT * height;
    }
    this.setTranslateY(y * height + padding - shift);
    currentY = y;
  }

  /**
   * Shifts the height by a specific percentage.
   * Helps with animation of the block being picked up.
   * @param percent double percentage value
   */
  public void setShiftHeight(double percent) {
    this.setTranslateY(currentY * height - height * PICKEDUP_SHIFT * percent + padding);
  }

  /**
   * Update number displayed on block.
   * @param num int value of the number that needs to be displayed on the block.
   */
  public void updateCubeNumber(int num) {
    this.getChildren().remove(blockText);
    String dummy = String.valueOf(num);
    blockText.setText(dummy);
    this.getChildren().add(blockText);
  }

  /**
   * Resets the block to its original position and resets the number displayed on the blcok.
   */
  public void reset() {
    this.setTranslateX(initialXCoordinate * width + padding);
    this.setTranslateY(initialYCoordinate * height + padding);
    currentX = initialXCoordinate;
    currentY = initialYCoordinate;
    updateCubeNumber(Integer.parseInt(number));
  }

  /**
   * Sets if the block is being held by an avatar or not.
   * @param status boolean representing if the block is being held or not
   */
  public void setHeldStatus(boolean status) {
    isHeld = status;
  }

  /**
   * Returns the initial x coordinate of the block.
   * @return int value of the initial x coordinate
   */
  public int getInitialXCoordinate() {
    return (int) currentX;
  }

  /**
   * Returns the initial y coordinate of the block.
   * @return int value of the initial y coordinate
   */
  public int getInitialYCoordinate() {
    return (int) currentY;
  }

  private void makeBlock() {
    block = new Rectangle(width - 2 * padding, height - 2 * padding);
    block.getStyleClass().add("block");

    block.setX(currentX);
    block.setY(currentY);
    blockText = new Text(number);
    blockText.getStyleClass().add("block-text");
    this.getChildren().add(block);
    this.getChildren().add(blockText);
    reset();
    spriteLayer.getChildren().add(this);
  }

}
