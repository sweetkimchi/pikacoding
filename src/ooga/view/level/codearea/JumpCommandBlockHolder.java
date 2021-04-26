package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ComboBox;

public class JumpCommandBlockHolder extends CommandBlockHolder {

  private ComboBox<String> lineSelector;

  public JumpCommandBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }

  @Override
  public void setIndex(int index) {
    super.setIndex(index);
    updateDropdown();
  }

  private void updateDropdown() {
//    getCommandBlock().setParameter(parameter, "1");
    lineSelector.getItems().clear();
    List<String> options = new ArrayList<>();
    for (int i = 1; i <= getCommandBlock().getIndex(); i++) {
      options.add(Integer.toString(i));
    }
    lineSelector.getItems().addAll(options);
//    lineSelector.getSelectionModel().selectFirst();
  }

  @Override
  protected void initializeDropdowns() {
    String parameter = getParameterOptions().get(0).keySet().iterator().next();
//    getCommandBlock().setParameter(parameter, "1");
    lineSelector = new ComboBox<>();
    updateDropdown();
    lineSelector.setOnAction(e -> {
      getCommandBlock().setParameter(parameter, lineSelector.getValue());
      getProgramStack().notifyProgramListeners();
    });
//    lineSelector.getSelectionModel().selectFirst();
    getDropdowns().put(parameter, lineSelector);
    addItem(lineSelector, 120);
  }

}
