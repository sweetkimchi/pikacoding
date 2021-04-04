package ooga.view.level.codearea;

import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.GridPane;

public class CodeArea extends GridPane {

  private CommandBank commandBank;
  private ProgramStack programStack;

  public CodeArea() {
    commandBank = new CommandBank(Arrays.asList("step"), this::addCommandBlock);
    programStack = new ProgramStack();
    this.add(commandBank, 0, 0);
    this.add(programStack, 1, 0);
  }

  public List<CommandBlock> getProgram() {
    return programStack.getProgram();
  }

  private void addCommandBlock(String command) {
    programStack.addCommandBlock(command);
  }

}
