package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Avatar {

  private static final String avatarImage = "images/PikachuAvatar.gif"; // TODO: put in resource file or get passed
  private int initialXCoordinate;
  private int initialYCoordinate;
  private double width;
  private double height;
  private SpriteLayer spriteLayer;
  private ImageView avatar;
  private int i = 0;
  private int k = 0;
  private int j = 0;

  public Avatar(int x, int y, double w, double h, SpriteLayer root) {
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    spriteLayer = root;
    makeAvatar();
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image("images/PikachuAvatar.gif"));
    avatar.getStyleClass().add("avatar");
    avatar.setFitWidth(width);
    avatar.setFitHeight(height);
    reset();
    spriteLayer.getChildren().add(avatar);
  }

  public void moveAvatar(double x, double y) {
    double currentX = avatar.getX();
    double currentY = avatar.getY();
    double nextX = x * width;
    double nextY = y * height;
    avatar.setX(x * width);
    avatar.setY(y * height);

    // TODO: refactor
    if(currentX < nextX) {
      int num = ((i) % 6) + 1;
      setAvatarImage("images/AnimatedPikachuRight" + num + ".gif");
      i++;
    }else if(nextX < currentX){
      int num = ((k) % 6) + 1;
      setAvatarImage("images/AnimatedPikachuLeft" + num + ".gif");
      k++;
    }
    else if(nextX == currentX && nextY == currentY){

//      setAvatarImage("AnimatedPikachuFDBK" + num + ".gif");
      setAvatarImage("images/PikachuAvatar.gif");

    }else if(nextX == currentX && nextY != currentY){
      int num = ((j) % 10) + 1;
      setAvatarImage("images/AnimatedPikachuFDBK" + num + ".gif");
      j++;
    }
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

  public void setAvatarImage(String image){
    avatar.setImage(new Image(image));
  }
}
