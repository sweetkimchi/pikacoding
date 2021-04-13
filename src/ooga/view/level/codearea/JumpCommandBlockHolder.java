package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ComboBox;

public class JumpCommandBlockHolder extends CommandBlockHolder {

  public JumpCommandBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }

  @Override
  protected Map<String, String> setInitialParameters(
      List<Map<String, List<String>>> parameterOptions) {
    Map<String, String> initialParameters = new HashMap<>();
    String parameter = parameterOptions.get(0).keySet().iterator().next();
    initialParameters.put(parameter, "1");
    return initialParameters;
  }

  @Override
  protected void initializeDropdowns() {
      String parameter = getParameterOptions().get(0).keySet().iterator().next();
      List<String> options = new ArrayList<>();
      for (int i = 1; i <= getCommandBlock().getIndex(); i++) {
        options.add(Integer.toString(i));
      }
      ComboBox<String> dropdown = new ComboBox<>();
      dropdown.getItems().addAll(options);
      dropdown.setOnAction(e -> {
        getCommandBlock().setParameter(parameter, dropdown.getValue());
      });
      dropdown.getSelectionModel().selectFirst();
      addItem(dropdown, 120);
  }
}
