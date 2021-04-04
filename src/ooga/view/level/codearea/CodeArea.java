package ooga.view.level.codearea;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * Area of the level view that displays the command bank and program stack.
 *
 * @author David Li
 */
public class CodeArea extends GridPane {

  private CommandBank commandBank;
  private ProgramStack programStack;

  public CodeArea() {
    // TODO: figure out which commands are available for this level from the json
    List<String> availableCommands = Collections.singletonList("step");
    commandBank = new CommandBank(availableCommands, this::addCommandBlock);
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
