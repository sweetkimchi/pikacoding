package ooga.view.level.codearea;

import java.util.List;
import java.util.Map;

/**
 * Command block holder for the start block of a nested command
 *
 * @author David Li
 */
public class NestedBeginBlockHolder extends CommandBlockHolder {

  private NestedEndBlockHolder endCommandBlockHolder;

  /**
   * Main constructor
   * @param index Index of the command
   * @param type The command type
   * @param parameterOptions A list of the parameters and their corresponding options
   * @param programStack The program stack
   */
  public NestedBeginBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }

  /**
   * Passes in the corresponding end block
   * @param endCommandBlockHolder The end block
   */
  public void attachEndHolder(NestedEndBlockHolder endCommandBlockHolder) {
    this.endCommandBlockHolder = endCommandBlockHolder;
  }

  public NestedEndBlockHolder getEndCommandBlockHolder() {
    return endCommandBlockHolder;
  }

  @Override
  protected void removeAction() {
//    getProgramStack().removeCommandBlock(endCommandBlockHolder.getIndex());
    getProgramStack().removeCommandBlock(getIndex());

    getProgramStack().notifyProgramListeners();
  }
}
