package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains all of the information for the command of this block.
 *
 * @author David Li
 */
public class CommandBlock {

  private int index;
  private String type;
  private Map<String, String> parameters;

  public CommandBlock(int index, String type, Map<String, String> parameters) {
    this.index = index;
    this.type = type;
    this.parameters = parameters;
  }

  public int getIndex() {
    return index;
  }

  public String getType() {
    return type;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameter(String parameter, String value) {
    assert parameters.containsKey(parameter);
    parameters.put(parameter, value);
  }

  void setIndex(int index) {
    this.index = index;
  }

}
