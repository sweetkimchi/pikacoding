package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ComboBox;

/**
 * Command block holder for the jump command
 *
 * @author David Li
 */
public class JumpCommandBlockHolder extends CommandBlockHolder {

  private ComboBox<String> lineSelector;

  /**
   * Main constructor
   * @param index Index of the command
   * @param type The command type
   * @param parameterOptions A list of the parameters and their corresponding options
   * @param programStack The program stack
   */
  public JumpCommandBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }

  /**
   * Changes the parameter dropdown based on new index
   * @param index New index
   */
  @Override
  public void setIndex(int index) {
    super.setIndex(index);
    updateDropdown();
  }

  private void updateDropdown() {
    getCommandBlock().setParameter("destination", "1");
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
    getCommandBlock().setParameter("destination", "1");
    String parameter = getParameterOptions().get(0).keySet().iterator().next();
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
