package ooga.view.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

/**
 * Interface that holds information on how to create buttons and labels.
 * Decreases the amount of duplicte code for making different GUI elements.
 * @author Kathleen Chen
 */
public interface GUIElementInterface {

  /**
   * Creates a button based on the parameters passed in.
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
  Button makeButton(ResourceBundle resourceBundle, EventHandler<ActionEvent> buttonAction, String key, String styleKey, String idKey, int num);

  /**
   * Creates a label based on the parameters passed in.
   * @param resourceBundle ResourceBundle containing the key and String for the label's text
   * @param key String key that corresponds the the button's text that can be found in the resource bundle
   * @param styleKey String style key that corresponds to the style class that
   *                 the button will be set to from the active css file
   * @param num int num that corresponds to the variable that could be displayed on the button
   *            (when being read from the resource file)
   * @return label that is created
   */
  Label makeLabel(ResourceBundle resourceBundle, String key, String styleKey, int num);
}
