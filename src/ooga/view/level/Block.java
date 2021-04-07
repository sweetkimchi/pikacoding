package ooga.view.level;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Block extends StackPane {
  private int row;
  private int col;
  private double width;
  private double height;
  private Text blockText;
  private GridPane grid;
  private String number;

  public Block(int r, int c, double w, double h, GridPane root, String num) {
    row = r;
    col = c;
    width = w;
    height = h;
    grid = root;
    number = num;
    makeBlock();
  }

  private void makeBlock() {
    Rectangle block = new Rectangle(width, height);
    block.setFill(Color.LIGHTSEAGREEN); // TODO: put in resource file
    blockText = new Text(number);
    this.getChildren().add(block);
    this.getChildren().add(blockText);
    grid.add(this, col, row);
  }

  public void pickUp(double r, double c) {
    this.setTranslateX(r + this.getTranslateX());
    this.setTranslateY(c + this.getTranslateY());
  }
}
