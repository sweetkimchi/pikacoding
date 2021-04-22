package ooga.view.level;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ooga.view.ScreenCreator;

import java.util.ResourceBundle;

public class MenuBar extends HBox {

  public MenuBar(EventHandler<ActionEvent> pauseAction) {
    ResourceBundle menuStrings = ResourceBundle.getBundle(ScreenCreator.RESOURCES + WinScreen.SCREEN_MESSAGES);
    this.getStyleClass().add("menu-bar");
    Button pauseButton = new Button(menuStrings.getString("pause"));
    pauseButton.getStyleClass().add("default-button");
    pauseButton.setId("pause-button");
    pauseButton.setOnAction(pauseAction);
    this.getChildren().add(pauseButton);
  }

}
