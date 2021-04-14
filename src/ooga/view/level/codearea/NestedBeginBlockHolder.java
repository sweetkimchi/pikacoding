package ooga.view.level.codearea;

import java.util.List;
import java.util.Map;

public class NestedBeginBlockHolder extends CommandBlockHolder {

  public NestedBeginBlockHolder(int index, String type,
      List<Map<String, List<String>>> parameterOptions,
      ProgramStack programStack) {
    super(index, type, parameterOptions, programStack);
  }
}
