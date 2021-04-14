package ooga.view.level.codearea;

import java.util.List;
import java.util.Map;

public class NestedBeginBlockHolder extends CommandBlockHolder {

  private NestedEndBlockHolder endCommandBlockHolder;

  public NestedBeginBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }

  public void attachEndHolder(NestedEndBlockHolder endCommandBlockHolder) {
    this.endCommandBlockHolder = endCommandBlockHolder;
  }

  @Override
  protected void removeAction(ProgramStack programStack) {
    programStack.removeCommandBlock(endCommandBlockHolder.getIndex());
    programStack.removeCommandBlock(getIndex());
  }
}
