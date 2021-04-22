package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import ooga.view.ScreenCreator;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Avatar extends StackPane {
  private static final String AVATAR_PROPERTIES = "Avatar";

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
  private int idNum;
  private Text avatarId;
  private double xPadding;
  private double yPadding;
  private ResourceBundle avatarFinalValues;

  public Avatar(int x, int y, double w, double h, int id, SpriteLayer root) {
    avatarFinalValues = ResourceBundle.getBundle(ScreenCreator.RESOURCES + AVATAR_PROPERTIES);
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    xPadding = Double.parseDouble(avatarFinalValues.getString("xFDBKRatio")) * width;
    yPadding = Double.parseDouble(avatarFinalValues.getString("yRatio")) * height;
    idNum = id;
    spriteLayer = root;
    animationImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES +
            avatarFinalValues.getString("imageResource"));
    makeAvatar();
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(animationImages.getString("defaultImage")));
    avatar.setFitWidth(width);
    avatar.setFitHeight(height);
    avatarId = new Text(String.valueOf(idNum));
    avatarId.getStyleClass().add("id");
    reset();
    spriteLayer.getChildren().add(avatar);
    spriteLayer.getChildren().add(avatarId);
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
      setAvatarImage(applyFormat(num, "rightImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xRightRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
      right++;
    } else if(nextX < currentX){
      int num = ((left) % Integer.parseInt(animationImages.getString("leftTotal"))) + 1;
      setAvatarImage(applyFormat(num, "leftImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xLeftRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
      left++;
    } else if(nextX == currentX && nextY == currentY){
      setAvatarImage(animationImages.getString("defaultImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xFDBKRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
    } else if(nextX == currentX && nextY != currentY){
      int num = ((fdbk) % Integer.parseInt(animationImages.getString("fdbkTotal"))) + 1;
      setAvatarImage(applyFormat(num, "fdbkImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xFDBKRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
      fdbk++;
    }
  }

  private String applyFormat(int num, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = num;
    formatter.applyPattern(animationImages.getString(key));
    return formatter.format(currNum);
  }

  public void reset() {
    avatar.setX(initialXCoordinate * width);
    avatar.setY(initialYCoordinate * height);
    avatarId.setX(initialXCoordinate * width + xPadding);
    avatarId.setY(initialYCoordinate * height + yPadding);
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
