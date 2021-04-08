package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Avatar {
  private static final String avatarImage = "PikachuAvatar.gif"; // TODO: put in resource file or get passed
  private int initialXCoordinate;
  private int initialYCoordinate;
  private double width;
  private double height;
  private SpriteLayer spriteLayer;
  private ImageView avatar;
  public Avatar(int x, int y, double w, double h, SpriteLayer root) {
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    spriteLayer = root;
    makeAvatar();
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(avatarImage));
    avatar.setFitWidth(width);
    avatar.setFitHeight(height);
    reset();
    spriteLayer.getChildren().add(avatar);
  }

  public void moveAvatar(double x, double y) {
    avatar.setX(x * width);
    avatar.setY(y * height);
  }

  public void reset() {
    avatar.setX(initialXCoordinate * width);
    avatar.setY(initialYCoordinate * height);
  }

  public int getInitialXCoordinate(){
    return (int) (avatar.getX()/width);
  }

  public int getInitialYCoordinate(){
    return (int) (avatar.getY()/height);
  }
}
