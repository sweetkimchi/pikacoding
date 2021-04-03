package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all of the information for the command of this block.
 *
 * @author David Li
 */
public abstract class CommandBlock {

  private int index;
  private List<Integer> parameters;

  public CommandBlock(int index) {
    this.index = index;
    parameters = new ArrayList<>();
  }

  public int getIndex() {
    return index;
  }

  public abstract String getType();

  public List<Integer> getParameters() {
    return parameters;
  }

  void setIndex(int index) {
    this.index = index;
  }

  protected abstract void initializeParameters();

}
