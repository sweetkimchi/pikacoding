package ooga.view.factories;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public interface GUIElementInterface {

  Button makeButton(ResourceBundle resourceBundle, EventHandler<ActionEvent> buttonAction, String key, String styleKey, String idKey, int num);

  Label makeLabel(ResourceBundle resourceBundle, String key, String styleKey, int num);
}
