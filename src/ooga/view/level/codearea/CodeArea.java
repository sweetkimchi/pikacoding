package ooga.view.level.codearea;

import java.util.Arrays;
import javafx.scene.layout.GridPane;

public class CodeArea extends GridPane {

  private CommandBank commandBank;

  public CodeArea() {
    commandBank = new CommandBank(Arrays.asList("step", "pickUp", "drop"), this::addCommandBlock);
    this.add(commandBank, 0, 0);
  }

  private void addCommandBlock(String command) {
    System.out.println("Adding command: " + command);
  }

}
