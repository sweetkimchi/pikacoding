package ooga.view.level.codearea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * JavaFX element that displays a CommandBlock. Shows line number, command type, and parameter
 * dropdown menus.
 *
 * @author David Li
 */
public class CommandBlockHolder extends GridPane {

  private static final double INDEX_WIDTH = 20;
  private static final String HAMBURGER_MENU_IMAGE = "ThreeLines.png";
  private static final double ITEM_HEIGHT = 20;

  private int index;
  private List<Map<String, List<String>>> parameterOptions;
  private Label indexLabel;
  private CommandBlock commandBlock;

  private int columns;

  public CommandBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions, Consumer<Integer> removeAction) {
    this.getStyleClass().add("command-block-holder");
    this.setHgap(5);
    this.columns = 0;
    indexLabel = new Label();
    indexLabel.getStyleClass().add("command-index");
    addItem(indexLabel, INDEX_WIDTH);
    indexLabel.setAlignment(Pos.CENTER);
    Label command = new Label(type);
    command.getStyleClass().add("command-block-name");
    addItem(command, 0);
    this.parameterOptions = parameterOptions;
    Map<String, String> initialParameters = new HashMap<>();
    parameterOptions.forEach(parameterOption -> {
      String parameter = parameterOption.keySet().iterator().next();
      initialParameters.put(parameter, parameterOption.get(parameter).get(0));
    });
    commandBlock = new CommandBlock(index, type, initialParameters);
    initializeDropdowns();

    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHgrow(Priority.ALWAYS);
    this.add(new Label(), columns++, 0);
    this.getColumnConstraints().add(columnConstraints);

    Button removeButton = new Button("x");
    removeButton.setPrefHeight(ITEM_HEIGHT);
    removeButton.setOnAction(e -> removeAction.accept(this.index));
    addItem(removeButton, 0);

    ImageView hamburgerMenu = new ImageView(new Image(HAMBURGER_MENU_IMAGE));
    hamburgerMenu.setFitHeight(ITEM_HEIGHT);
    hamburgerMenu.setFitWidth(ITEM_HEIGHT);
    addItem(hamburgerMenu, 0);

    setIndex(index);
    this.setPadding(new Insets(4, 4, 4, 4));
  }

  public CommandBlock getCommandBlock() {
    return commandBlock;
  }

  public void setIndex(int index) {
    this.index = index;
    commandBlock.setIndex(index);
    String prefix = "";
    if (index < 10) {
      prefix = "0";
    }
    indexLabel.setText(prefix + index);
  }

  private void addItem(Node node, double width) {
    ColumnConstraints columnConstraints = new ColumnConstraints();
    if (width > 0) {
      columnConstraints.setPrefWidth(width);
    }
    this.add(node, columns++, 0);
    this.getColumnConstraints().add(columnConstraints);
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
      addItem(dropdown, 120);
    });
  }

}
