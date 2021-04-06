package ooga.view.level.codearea;

import java.util.Collections;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import ooga.model.commands.AvailableCommands;

/**
 * Area of the level view that displays the command bank and program stack.
 *
 * @author David Li
 */
public class CodeArea extends GridPane {

  private CommandBank commandBank;
  private ProgramStack programStack;

  public CodeArea() {
    commandBank = new CommandBank(this::addCommandBlock);
    programStack = new ProgramStack();
    ScrollPane programStackHolder = new ScrollPane();
    programStackHolder.setFitToWidth(true);
    programStackHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setContent(programStack);
    this.add(commandBank, 0, 0);
    this.add(programStackHolder, 1, 0);
  }

  public List<CommandBlock> getProgram() {
    return programStack.getProgram();
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    commandBank.addCommands(availableCommands.getCommandNames());
    programStack.setAvailableCommands(availableCommands);
  }

  private void addCommandBlock(String command) {
    programStack.addCommandBlock(command);
  }

}
