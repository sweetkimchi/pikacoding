package ooga.view;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;
import ooga.view.level.WinScreen;

public class LevelSelector extends BorderPane {
  private GUIElementInterface GUIFactory;

  public LevelSelector(Consumer<Integer> loadLevelAction) {
    GUIFactory = new GUIElementFactory();
    VBox levels = new VBox();
    ResourceBundle levelResources = ResourceBundle.getBundle(ScreenCreator.RESOURCES + WinScreen.SCREEN_MESSAGES);

    for (int level = 1; level <= Integer.parseInt(levelResources.getString("maxNum")); level++) {
      int currentLevel = level;
      Button levelButton = GUIFactory.makeButton(levelResources, e -> loadLevelAction.accept(currentLevel), "level", "default-button", "levelButton", level);
      levels.getChildren().add(levelButton);
    }

    levels.getStyleClass().add("level-selector");
    this.setCenter(levels);
  }

}
