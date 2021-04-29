package ooga.view.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.view.ScreenCreator;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * GUI element factory that implements the GUIElementInterface.
 * Contains information on how to make buttons and labels.
 * Decreases the amount of duplicate code when making different kind of GUI elements.
 * @author Kathleen Chen
 */
public class GUIElementFactory implements GUIElementInterface {
  private static final String IDS_FOR_TESTING = ScreenCreator.RESOURCES + "IdsForTesting";
  private static final ResourceBundle IDS_RESOURCE = ResourceBundle.getBundle(IDS_FOR_TESTING);

  /**
   * Main constructor.
   */
  public GUIElementFactory() {

  }

  /**
   * Contains information on how to create a button based on passed in parameters.
   * @param resourceBundle ResourceBundle containing the key and String for the button's text
   * @param buttonAction EventHandler that the button's setOnAction will be set to
   * @param key String key that corresponds the the button's text that can be found in the resource bundle
   * @param styleKey String style key that corresponds to the style class that
   *                 the button will be set to from the active css file
   * @param idKey String key that corresponds the the button's testing id found in the IdsForTesting resource bundle
   * @param num int num that corresponds to the variable that could be displayed on the button
   *            (when being read from the resource file)
   * @return button that is created
   */
  @Override
  public Button makeButton(ResourceBundle resourceBundle, EventHandler<ActionEvent> buttonAction, String key, String styleKey, String idKey, int num) {
    Button button = new Button(readPropertiesFile(num, resourceBundle.getString(key)));
    button.getStyleClass().add(styleKey);
    button.setOnAction(buttonAction);
    button.setId(readPropertiesFile(num, IDS_RESOURCE.getString(idKey)));
    return button;
  }

  /**
   * Contains information on how to create a label based on passed in parameters.
   * @param resourceBundle ResourceBundle containing the key and String for the label's text
   * @param key String key that corresponds the the button's text that can be found in the resource bundle
   * @param styleKey String style key that corresponds to the style class that
   *                 the button will be set to from the active css file
   * @param num int num that corresponds to the variable that could be displayed on the button
   *            (when being read from the resource file)
   * @return label that is created
   */
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
