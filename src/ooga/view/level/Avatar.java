package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.view.ScreenCreator;

import java.util.ResourceBundle;

public class Avatar {
  private static final String IMAGE_PROPERTIES = "AnimationImages";
  private int initialXCoordinate;
  private int initialYCoordinate;
  private double width;
  private double height;
  private SpriteLayer spriteLayer;
  private ResourceBundle animationImages;
  private ImageView avatar;
  private int right = 0;
  private int left = 0;
  private int fdbk = 0;

  public Avatar(int x, int y, double w, double h, SpriteLayer root) {
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    spriteLayer = root;
    animationImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + IMAGE_PROPERTIES);
    makeAvatar();
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(animationImages.getString("defaultImage") + ".gif"));
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
      int num = ((right) % Integer.parseInt(animationImages.getString("rightTotal"))) + 1;
      setAvatarImage(animationImages.getString("rightImage") + num + ".gif");
      right++;
    }else if(nextX < currentX){
      int num = ((left) % Integer.parseInt(animationImages.getString("leftTotal"))) + 1;
      setAvatarImage(animationImages.getString("leftImage") + num + ".gif");
      left++;
    }
    else if(nextX == currentX && nextY == currentY){
      setAvatarImage(animationImages.getString("defaultImage") + ".gif");

    } else if(nextX == currentX && nextY != currentY){
      int num = ((fdbk) % Integer.parseInt(animationImages.getString("fdbkTotal"))) + 1;
      setAvatarImage(animationImages.getString("fdbkImage") + num + ".gif");
      fdbk++;
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
