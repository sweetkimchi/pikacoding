package ooga.view.level.codearea;

import java.util.Map;
import javafx.scene.web.HTMLEditorSkin.Command;

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

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CommandBlock commandBlock)  {
      return (this.index == commandBlock.index &&
          this.type.equals(commandBlock.type) &&
          this.parameters.equals(commandBlock.parameters));
    }
    return false;
  }
}
