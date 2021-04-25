package ooga.view.level.codearea;

import java.util.ArrayList;

public class NestedEndBlockHolder extends CommandBlockHolder {

  private NestedBeginBlockHolder beginCommandBlockHolder;

  public NestedEndBlockHolder(int index, String type,
      ProgramStack programStack) {
    super(index, "end " + type, new ArrayList<>(), programStack);
  }

  public void attachBeginHolder(NestedBeginBlockHolder beginCommandBlockHolder) {
    this.beginCommandBlockHolder = beginCommandBlockHolder;
  }

  public NestedBeginBlockHolder getBeginCommandBlockHolder() {
    return beginCommandBlockHolder;
  }

  @Override
  protected void removeAction() {
    getProgramStack().removeCommandBlock(beginCommandBlockHolder.getIndex());
    getProgramStack().removeCommandBlock(getIndex());
    getProgramStack().notifyProgramListeners();
  }

}
