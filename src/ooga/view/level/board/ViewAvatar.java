package ooga.view.level.board;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import ooga.view.ScreenCreator;

/**
 * Creates each avatar that is displayed on the board.
 * Contains the methods to move/update created avatar.
 *
 * @author Kathleen Chen
 * @author David Li
 */
public class ViewAvatar extends StackPane {

  private static final String AVATAR_PROPERTIES = "ViewAvatar";

  private final int initialXCoordinate;
  private final int initialYCoordinate;
  private final double width;
  private final double height;
  private final SpriteLayer spriteLayer;
  private final ResourceBundle animationImages;
  private ImageView avatar;
  private final int rightNum = 0;
  private final int leftNum = 0;
  private final int fdbkNum = 0;
  private final int idNum;
  private Text avatarId;
  private double xPadding;
  private final double yPadding;
  private final ResourceBundle avatarFinalValues;
  private HashMap<String, Integer> variables;

  /**
   * Main constructor.
   * @param x x coordinate int value
   * @param y y coordinate int value
   * @param w width double value
   * @param h height double value
   * @param id int value of id
   * @param root SpriteLayer which the avatar will be displayed on
   */
  public ViewAvatar(int x, int y, double w, double h, int id, SpriteLayer root) {
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

  /**
   * Moves the avatar based on the x and y distances.
   * Contains information on which image should be displayed based on the direction the avatar is moving.
   * @param x double distance that the avatar will move in the x direction
   * @param y double distance that the avatar will move in the y direction
   */
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
    boolean fdbk = nextX == currentX && nextY != currentY;

    if (right) {
      animationChanges("right", x, y);
    } else if (left) {
      animationChanges("left", x, y);
    } else if (fdbk) {
      animationChanges("fdbk", x, y);
    } else if (base) {
      setAvatarImage(animationImages.getString("baseImage"));
      xPadding = Double.parseDouble(avatarFinalValues.getString("xBaseRatio")) * width;
      avatarId.setX(x * width + xPadding);
      avatarId.setY(y * height + yPadding);
    }
  }

  /**
   * Resets the avatar to its original position.
   */
  public void reset() {
    avatar.setX(initialXCoordinate * width);
    avatar.setY(initialYCoordinate * height);
    avatarId.setX(initialXCoordinate * width + xPadding);
    avatarId.setY(initialYCoordinate * height + yPadding);
  }

  /**
   * Returns the initial x coordinate of the avatar.
   * @return int value of the initial x coordinate
   */
  public int getInitialXCoordinate() {
    return (int) (avatar.getX() / width);
  }

  /**
   * Returns the initial y coordinate of the avatar.
   * @return int value of the initial y coordinate
   */
  public int getInitialYCoordinate() {
    return (int) (avatar.getY() / height);
  }

  /**
   * Sets the image of the avatar.
   * @param image String name of the image
   */
  public void setAvatarImage(String image) {
    avatar.setImage(new Image(image));
  }

  private void makeVariableMap() {
    variables = new HashMap();
    variables.put("rightNum", rightNum);
    variables.put("leftNum", leftNum);
    variables.put("fdbkNum", fdbkNum);
  }

  private void makeAvatar() {
    avatar = new ImageView(new Image(animationImages.getString("baseImage")));
    avatar.setId(ScreenCreator.idsForTests.getString("avatar"));
    avatar.setFitWidth(width);
    avatar.setFitHeight(height);
    avatarId = new Text(String.valueOf(idNum));
    avatarId.getStyleClass().add("id");
    reset();
    spriteLayer.getChildren().add(avatar);
    spriteLayer.getChildren().add(avatarId);
  }

  private void animationChanges(String direction, double x, double y) {
    int num = ((variables.get(direction + "Num")) %
        Integer.parseInt(animationImages.getString(direction + "Total"))) + 1;
    setAvatarImage(applyResourceFormat(num, direction + "Image"));
    xPadding = Double.parseDouble(avatarFinalValues.getString("x" + direction + "Ratio")) * width;
    avatarId.setX(x * width + xPadding);
    avatarId.setY(y * height + yPadding);
    variables.replace(direction + "Num", (variables.get(direction + "Num") + 1));
  }

  private String applyResourceFormat(int num, String key) {
    Object[] currNum = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    currNum[0] = num;
    formatter.applyPattern(animationImages.getString(key));
    return formatter.format(currNum);
  }

}
