package ooga.view.level.board;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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

  public void setShiftHeight(double percent) {
    this.setTranslateY(currentY * height - height * PICKEDUP_SHIFT * percent + padding);
  }

  /**
   * Purpose: update number on datacube based on what backend/controller passes.
   *
   * @param num
   */
  public void updateCubeNumber(int num) {
    this.getChildren().remove(blockText);
    String dummy = String.valueOf(num);
    blockText.setText(dummy);
    this.getChildren().add(blockText);
  }

  public void reset() {
    this.setTranslateX(initialXCoordinate * width + padding);
    this.setTranslateY(initialYCoordinate * height + padding);
    currentX = initialXCoordinate;
    currentY = initialYCoordinate;
    updateCubeNumber(Integer.parseInt(number));
  }

  public void setHeldStatus(boolean status) {
    isHeld = status;
  }

  public int getInitialXCoordinate() {
    return (int) currentX;
  }

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
