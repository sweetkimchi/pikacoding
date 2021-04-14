package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedEndBlockHolder extends CommandBlockHolder {

  public NestedEndBlockHolder(int index, String type,
      ProgramStack programStack) {
    super(index, "end " + type, new ArrayList<>(), programStack);
  }
}
