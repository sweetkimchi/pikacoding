package ooga.view.level.codearea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * JavaFX element that displays a CommandBlock. Shows line number, command type, and parameter
 * dropdown menus.
 *
 * @author David Li
 */
public class CommandBlockHolder extends GridPane {

  private static final double INDEX_WIDTH = 20;
  private static final double COMMAND_WIDTH = 60;

  private int index;
  private String type;
  private List<Map<String, List<String>>> parameterOptions;
  private Label indexLabel;
  private CommandBlock commandBlock;

  private int columns;

  public CommandBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions, Consumer<Integer> removeAction) {
    this.getStyleClass().add("command-block-holder");
    this.setHgap(5);
//    this.setGridLinesVisible(true);
    this.index = index;
    this.type = type;
    this.columns = 0;
    indexLabel = new Label("" + index);
    indexLabel.setPrefWidth(INDEX_WIDTH);
    addItem(indexLabel);
    indexLabel.setAlignment(Pos.CENTER);
    Label command = new Label(type);
    command.setPrefWidth(COMMAND_WIDTH);
    addItem(command);
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
    addItem(removeButton);

    this.setPadding(new Insets(4, 4, 4, 4));
  }

  public CommandBlock getCommandBlock() {
    return commandBlock;
  }

  public void setIndex(int index) {
    this.index = index;
    commandBlock.setIndex(index);
    indexLabel.setText("" + index);
  }

  private void addItem(Node node) {
    this.add(node, columns++, 0);
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
      addItem(dropdown);
    });
  }

}
