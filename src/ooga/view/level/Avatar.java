package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import ooga.view.ScreenCreator;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
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
  private int rightNum = 0;
  private int leftNum = 0;
  private int fdbkNum = 0;
  private int idNum;
  private Text avatarId;
  private double xPadding;
  private double yPadding;
  private ResourceBundle avatarFinalValues;
  private HashMap<String, Integer> variables;

  public Avatar(int x, int y, double w, double h, int id, SpriteLayer root) {
    avatarFinalValues = ResourceBundle.getBundle(ScreenCreator.RESOURCES + AVATAR_PROPERTIES);
    initialXCoordinate = x;
    initialYCoordinate = y;
    width = w;
    height = h;
    xPadding = Double.parseDouble(avatarFinalValues.getString("xfdbkRatio")) * width;
    yPadding = Double.parseDouble(avatarFinalValues.getString("yRatio")) * height;
    idNum = id;
    spriteLayer = root;
    animationImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES +
            avatarFinalValues.getString("imageResource"));
    makeVariableMap();
    makeAvatar();
  }

  private void makeVariableMap() {
    variables = new HashMap();
    variables.put("rightNum", rightNum);
    variables.put("leftNum", leftNum);
    variables.put("fdbkNum", fdbkNum);
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(animationImages.getString("baseImage")));
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

    boolean right = currentX < nextX;
    boolean left = nextX < currentX;
    boolean base = nextX == currentX && nextY == currentY;
    boolean fdbk =nextX == currentX && nextY != currentY;

    // TODO: refactor
    if(right) {
      animationChanges("right", x, y);
    } else if(left){
      animationChanges("left", x, y);
    } else if(fdbk){
      animationChanges("fdbk", x, y);
    } else if(base) {
      setAvatarImage(animationImages.getString("baseImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xBaseRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
    }
  }

  private void animationChanges(String direction, double x, double y) {
    int num = ((variables.get(direction + "Num")) % Integer.parseInt(animationImages.getString(direction + "Total"))) + 1;
    setAvatarImage(applyFormat(num, direction + "Image"));
    xPadding = Double.parseDouble(avatarFinalValues.getString("x" + direction + "Ratio")) * width;
    avatarId.setX(x * width + xPadding);
    avatarId.setY(y * height + yPadding);
    variables.replace(direction + "Num", (variables.get(direction + "Num") + 1));
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
