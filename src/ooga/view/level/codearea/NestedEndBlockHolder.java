package ooga.view.level.codearea;

import java.util.ArrayList;

/**
 * Command block holder for the end block of a nested command
 *
 * @author David Li
 */
public class NestedEndBlockHolder extends CommandBlockHolder {

  private NestedBeginBlockHolder beginCommandBlockHolder;

  /**
   * Main constructor
   * @param index Index of the command
   * @param type The command type
   * @param programStack The program stack
   */
  public NestedEndBlockHolder(int index, String type,
      ProgramStack programStack) {
    super(index, "end " + type, new ArrayList<>(), programStack);
  }

  /**
   * Passes in the corresponding start block
   * @param beginCommandBlockHolder The start block
   */
  public void attachBeginHolder(NestedBeginBlockHolder beginCommandBlockHolder) {
    this.beginCommandBlockHolder = beginCommandBlockHolder;
  }

  public NestedBeginBlockHolder getBeginCommandBlockHolder() {
    return beginCommandBlockHolder;
  }

  @Override
  protected void removeAction() {
//    getProgramStack().removeCommandBlock(beginCommandBlockHolder.getIndex());
    getProgramStack().removeCommandBlock(getIndex());
    getProgramStack().notifyProgramListeners();
  }

}
