package ooga.view.level;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Block extends StackPane {

  private static final double PADDING_RATIO = 0.25;
  private static final double PICKEDUP_SHIFT = 0.5;

  private int initialXCoordinate;
  private int initialYCoordinate;
  private double width;
  private double height;
  private double padding;
  private Text blockText;
  private SpriteLayer spriteLayer;
  private String number;

  public Block(int x, int y, double w, double h, SpriteLayer root, String num) {
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    padding = PADDING_RATIO * width;
    spriteLayer = root;
    number = num;
    makeBlock();
  }

  public void moveBlock(int x, int y) {
    this.setTranslateX(x * width + padding);
    this.setTranslateY(y * height + padding);
  }

  public void animatePickUp(double percent) {

  }

  public void animateDrop(double percent) {

  }

  public void reset() {
    this.setTranslateX(initialXCoordinate * width + padding);
    this.setTranslateY(initialYCoordinate * height + padding);
  }

  private void makeBlock() {
    Rectangle block = new Rectangle(width - 2 * padding, height - 2 * padding);
    block.setFill(Color.LIGHTSEAGREEN); // TODO: put in resource file
    blockText = new Text(number);
    this.getChildren().add(block);
    this.getChildren().add(blockText);
    reset();
    spriteLayer.getChildren().add(this);
  }

  public void pickUp(double r, double c) {
    this.setTranslateX(r + this.getTranslateX());
    this.setTranslateY(c + this.getTranslateY());
  }
}
