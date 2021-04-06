package ooga.view.level.codearea;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * JavaFX element that displays a CommandBlock.
 * Shows line number, command type, and parameter dropdown menus.
 *
 * @author David Li
 */
public class CommandBlockHolder extends HBox {

  private int index;
  private String type;
  private List<Map<String, List<String>>> parameterOptions;
  private Label label;
  private CommandBlock commandBlock;

  public CommandBlockHolder(int index, String type, List<Map<String, List<String>>> parameterOptions, Consumer<Integer> removeAction) {
    this.setSpacing(4);

    this.index = index;
    this.type = type;
    label = new Label(index + " " + type);
    this.getChildren().add(label);
    this.parameterOptions = parameterOptions;
    Map<String, String> initialParameters = new HashMap<>();
    parameterOptions.forEach(parameterOption -> {
      String parameter = parameterOption.keySet().iterator().next();
      initialParameters.put(parameter, parameterOption.get(parameter).get(0));
    });
    commandBlock = new CommandBlock(index, type, initialParameters);
    initializeDropdowns();
    Button removeButton = new Button("x");
    removeButton.setOnAction(e -> removeAction.accept(this.index));
    this.getChildren().add(removeButton);
  }

  public CommandBlock getCommandBlock() {
    return commandBlock;
  }

  public void setIndex(int index) {
    this.index = index;
    commandBlock.setIndex(index);
    label.setText(index + " " + type);
  }

  private void initializeDropdowns() {
    parameterOptions.forEach(parameterOption -> {
      String parameter = parameterOption.keySet().iterator().next();
      List<String> options = parameterOption.get(parameter);
      ComboBox<String> dropdown = new ComboBox<>();
      dropdown.getItems().addAll(options);
      dropdown.setOnAction(e -> {
        commandBlock.setParameter(parameter, dropdown.getValue());
      });
      dropdown.getSelectionModel().selectFirst();
      this.getChildren().add(dropdown);
    });
  }

}
