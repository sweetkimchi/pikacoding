package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedEndBlockHolder extends CommandBlockHolder {

  private NestedBeginBlockHolder beginCommandBlockHolder;

  public NestedEndBlockHolder(int index, String type,
      ProgramStack programStack) {
    super(index, "end " + type, new ArrayList<>(), programStack);
  }

  public void attachBeginHolder(NestedBeginBlockHolder beginCommandBlockHolder) {
    this.beginCommandBlockHolder = beginCommandBlockHolder;
  }

  @Override
  protected void removeAction(ProgramStack programStack) {
    programStack.removeCommandBlock(beginCommandBlockHolder.getIndex());
    programStack.removeCommandBlock(getIndex());
  }
}
