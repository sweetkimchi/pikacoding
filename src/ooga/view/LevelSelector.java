package ooga.view;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

public class LevelSelector extends BorderPane {

  public LevelSelector(Consumer<Integer> loadLevelAction) {
    VBox levels = new VBox();
    for (int i = 1; i <= Controller.NUM_LEVELS; i++) {
      int level = i;
      Button levelButton = new Button("Level " + level);
      levelButton.setId("load-level-" + level);
      levelButton.setOnAction(e -> loadLevelAction.accept(level));
      levels.getChildren().add(levelButton);
    }
    levels.getStyleClass().add("level-selector");
    this.setCenter(levels);
  }

}
