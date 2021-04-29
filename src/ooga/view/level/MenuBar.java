package ooga.view.level;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import ooga.view.ScreenCreator;

/**
 * Menu bar displayed at the top of a level screen
 *
 * @author David Li
 */
public class MenuBar extends HBox {

  /**
   * Main constructor
   * @param pauseAction Action for pausing the game
   */
  public MenuBar(EventHandler<ActionEvent> pauseAction) {
    ResourceBundle menuStrings = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + WinScreen.SCREEN_MESSAGES);
    this.getStyleClass().add("menu-bar");
    Button pauseButton = new Button(menuStrings.getString("pause"));
    pauseButton.getStyleClass().add("default-button");
    pauseButton.setId(ScreenCreator.idsForTests.getString("pauseButton"));
    pauseButton.setOnAction(pauseAction);
    this.getChildren().add(pauseButton);
  }

}
