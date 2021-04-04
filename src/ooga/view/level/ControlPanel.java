package ooga.view.level;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ooga.view.ScreenCreator;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class ControlPanel extends GridPane {
  private static final String BUTTON_IMAGES = "ControlPanelButtons";
  private static final int ICON_SIZE = 20; //TODO: put in properties file
  private Object[] buttons;
  private ResourceBundle buttonImages;

  public ControlPanel() {
    this.getStyleClass().add("control-panel");
    buttonImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + BUTTON_IMAGES);
    buttons = buttonImages.keySet().toArray();
    makeButtons();
  }

  private void makeButtons() {
    int col = 0;
    for(Object o: buttons) {
      Button button = new Button();
      button.setGraphic(setIcon(buttonImages.getString(o.toString())));
//    button.getStyleClass().add(BUTTON);
//    button.setId(idsForTesting.getString(key));
      button.setOnAction(event -> reflectionMethod(o.toString()));
      this.add(button, col, 0);
      col ++;
    }
  }

  private void reflectionMethod(String key) {
    try {
//      String methodName = reflectionResource.getString(key);
//      Method m = UserCommandPane.this.getClass().getDeclaredMethod(methodName);
//      m.invoke(UserCommandPane.this);
    } catch (Exception e) {
      new Alert(Alert.AlertType.ERROR);
    }
  }

  private ImageView setIcon(String icon) {
    ImageView iconView = new ImageView(new Image(icon));
    iconView.setFitWidth(ICON_SIZE);
    iconView.setFitHeight(ICON_SIZE);
    return iconView;
  }

}
