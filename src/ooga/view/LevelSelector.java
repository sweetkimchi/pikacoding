package ooga.view;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
      Button levelButton = new Button(applyResourceFormatting(level, levelResources.getString("level")));
      levelButton.setId(applyResourceFormatting(level, ScreenCreator.idsForTests.getString("levelButton")));
      levelButton.getStyleClass().add("default-button");
      int thisLevel = level;
      try{
        levelButton.setOnAction(e -> loadLevelAction.accept(thisLevel));
      }catch(Exception error){

      }
      levels.getChildren().add(levelButton);
    }
    levels.getStyleClass().add("level-selector");
    this.setCenter(levels);
  }

  private String applyResourceFormatting(int num, String key) {
    Object[] var = new Object[1];
    MessageFormat formatter = new MessageFormat("");
    var[0] = num;
    formatter.applyPattern(key);
    return formatter.format(var);
  }

}
