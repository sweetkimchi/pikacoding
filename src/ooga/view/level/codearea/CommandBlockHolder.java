package ooga.view.level.codearea;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class CommandBlockHolder extends HBox {

  private int index;
  private String type;
  private Map<String, List<String>> parameterOptions;
  private CommandBlock commandBlock;

  public CommandBlockHolder(int index, String type) {
    this.index = index;
    this.type = type;
    parameterOptions = parseParameterOptions(type);
    initializeDropdowns();
    Map<String, String> initialParameters = new HashMap<>();
    parameterOptions.forEach((parameter, options) -> {
      initialParameters.put(parameter, options.get(0));
    });
    commandBlock = new CommandBlock(index, type, initialParameters);
  }

  private void initializeDropdowns() {
    parameterOptions.forEach((parameter, options) -> {
      ComboBox<String> dropdown = new ComboBox<>();
      dropdown.getItems().addAll(options);
    });
  }

  private Map<String, List<String>> parseParameterOptions(String type) {
    // TODO: Parse options from json file
    Map<String, List<String>> options = new LinkedHashMap<>();
    if (type.equals("step")) {
      options.put("direction", Arrays.asList( "up",
          "up-right",
          "right",
          "down-right",
          "down",
          "down-left",
          "left",
          "up-left"));
    }
    return options;
  }

}
