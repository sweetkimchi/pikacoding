package ooga.view;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;
import ooga.view.level.WinScreen;

public class LevelSelector extends BorderPane {

  public LevelSelector(Consumer<Integer> loadLevelAction) {
    VBox levels = new VBox();
    ResourceBundle levelResources = ResourceBundle.getBundle(ScreenCreator.RESOURCES + WinScreen.SCREEN_MESSAGES);
    for (int level = 1; level <= Controller.NUM_LEVELS; level++) {
      Button levelButton = new Button(getLevel(level, levelResources.getString("level")));
      levelButton.setId("load-level-" + level);
      levelButton.getStyleClass().add("default-button");
      int thisLevel = level;
      levelButton.setOnAction(e -> loadLevelAction.accept(thisLevel));
      levels.getChildren().add(levelButton);
    }
    levels.getStyleClass().add("level-selector");
    this.setCenter(levels);
  }

  private String getLevel(int l, String key) {
    Object[] level = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    level[0] = l;
    formatter.applyPattern(key);
    return formatter.format(level);
  }

}
