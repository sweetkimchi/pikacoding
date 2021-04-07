package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Avatar {
  private static final String avatarImage = "PikachuAvatar.gif"; // TODO: put in resource file or get passed
  private int row;
  private int col;
  private double width;
  private double height;
  private GridPane grid;
  private ImageView avatar;
  public Avatar(int r, int c, double w, double h, GridPane root) {
    row = r;
    col = c;
    width = w;
    height = h;
    grid = root;
    makeAvatar();
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(avatarImage));
    avatar.setFitWidth(width);
    avatar.setFitHeight(height);
    grid.add(avatar, col, row);
  }

  public void moveAvatar(double r, double c) {
    avatar.setTranslateX(width + avatar.getTranslateX());
    avatar.setTranslateY(height + avatar.getTranslateY());
//    avatar.setX(r);
//    avatar.setY(c);
    System.out.println("Avatar xCoord: " + avatar.getTranslateX());
  }

  public void reset() {
    avatar.setTranslateX(0);
    avatar.setTranslateY(0);
  }
}
