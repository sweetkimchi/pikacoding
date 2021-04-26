package ooga.view.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.view.ScreenCreator;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class GUIElementFactory implements GUIElementInterface {
  private static final String IDS_FOR_TESTING = ScreenCreator.RESOURCES + "IdsForTesting";
  private static final ResourceBundle IDS_RESOURCE = ResourceBundle.getBundle(IDS_FOR_TESTING);

  public GUIElementFactory() {

  }

  @Override
  public Button makeButton(ResourceBundle resourceBundle, EventHandler<ActionEvent> buttonAction, String key, String styleKey, String idKey, int num) {
    Button button = new Button(readPropertiesFile(num, resourceBundle.getString(key)));
    button.getStyleClass().add(styleKey);
    button.setOnAction(buttonAction);
    button.setId(readPropertiesFile(num, IDS_RESOURCE.getString(idKey)));
    return button;
  }

  @Override
  public Label makeLabel(ResourceBundle resourceBundle, String key, String styleKey, int num) {
    Label label = new Label(readPropertiesFile(num, resourceBundle.getString(key)));
    label.getStyleClass().add(styleKey);
    return label;
  }

  private String readPropertiesFile(int num, String key) {
    Object[] var = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    var[0] = num;
    formatter.applyPattern(key);
    return formatter.format(var);
  }
}
