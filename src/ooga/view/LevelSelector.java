package ooga.view;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LevelSelector extends BorderPane {

  private static final int NUM_LEVELS = 3;

  public LevelSelector(Consumer<Integer> loadLevelAction) {
    VBox levels = new VBox();
    for (int level = 1; level <= NUM_LEVELS; level++) {
      Button levelButton = new Button("Level " + level);
      int thisLevel = level;
      levelButton.setOnAction(e -> loadLevelAction.accept(thisLevel));
      levels.getChildren().add(levelButton);
    }
    this.setCenter(levels);
    BorderPane.setAlignment(levels, Pos.CENTER);
  }

}
