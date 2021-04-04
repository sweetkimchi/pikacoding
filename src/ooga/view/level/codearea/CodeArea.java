package ooga.view.level.codearea;

import java.util.Arrays;
import javafx.scene.layout.GridPane;

public class CodeArea extends GridPane {

  private CommandBank commandBank;

  public CodeArea() {
    commandBank = new CommandBank(Arrays.asList("step"), this::addCommandBlock);
    this.add(commandBank, 0, 0);
  }

  private void addCommandBlock(String command) {
    CommandBlockHolder commandBlockHolder = new CommandBlockHolder(0, command);
    this.add(commandBlockHolder, 1, 0);
  }

}
