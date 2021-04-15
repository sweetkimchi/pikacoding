package ooga.view;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

public class LevelSelector extends BorderPane {

  public LevelSelector(Consumer<Integer> loadLevelAction) {
    this.getStylesheets().add(StartMenu.DEFAULT_CSS);
    VBox levels = new VBox();
    for (int level = 1; level <= Controller.NUM_LEVELS; level++) {
      Button levelButton = new Button("Level " + level);
      levelButton.setId("load-level-" + level);
      int thisLevel = level;
      levelButton.setOnAction(e -> loadLevelAction.accept(thisLevel));
      levels.getChildren().add(levelButton);
    }
    levels.getStyleClass().add("level-selector");
    this.setCenter(levels);
  }

}
