package ooga.view.level.codearea;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import ooga.model.commands.AvailableCommands;
import ooga.view.ScreenCreator;
import ooga.view.level.LevelView;

/**
 * Area of the level view that displays the command bank and program stack.
 *
 * @author David Li
 */
public class CodeArea extends GridPane {

  private static final String CODEAREA_PROPERTIES = "CodeArea";

  private final CommandBank commandBank;
  private final ProgramStack programStack;

  /**
   * Main constructor
   */
  public CodeArea() {
    commandBank = new CommandBank(this::addCommandBlock);
    programStack = new ProgramStack();
    ScrollPane commandBankHolder = generateScrollPane(commandBank);
    ScrollPane programStackHolder = generateScrollPane(programStack);
    this.add(commandBankHolder, 0, 0);
    this.add(programStackHolder, 1, 0);
    ResourceBundle sizeProperties = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + CODEAREA_PROPERTIES);
    double commandBankWidth = Double.parseDouble(sizeProperties.getString("CommandBankWidth"));
    sizeProperties = ResourceBundle.getBundle(ScreenCreator.RESOURCES + LevelView.LEVEL_PROPERTIES);
    double programWidth =
        Double.parseDouble(sizeProperties.getString("CodeAreaWidth")) - commandBankWidth;
    commandBank.setPrefWidth(commandBankWidth);
    programStack.setPrefWidth(programWidth);
    this.setHgap(8);
  }

  /**
   * Sets which line each avatar is running
   * @param lineNumbers Map from avatar ids to line numbers
   */
  public void setLineIndicators(Map<Integer, Integer> lineNumbers) {
    programStack.setLineIndicators(lineNumbers);
  }

  public List<CommandBlock> getProgram() {
    return programStack.getProgram();
  }

  /**
   * Adds the available commands to the command bank and sets the available commands for the program
   * @param availableCommands Available commands
   */
  public void setAvailableCommands(AvailableCommands availableCommands) {
    commandBank.addCommands(availableCommands.getCommandNames());
    programStack.setAvailableCommands(availableCommands);
  }

  /**
   * Adds the commands as the teammate's commands to the command bank and sets the other player's
   * commands in the program
   * @param availableCommands Available commands for the other player
   */
  public void setAvailableCommandsOtherPlayer(AvailableCommands availableCommands) {
    commandBank.addCommandsOtherPlayer(availableCommands.getCommandNames());
    programStack.setAvailableCommandsOtherPlayer(availableCommands);
  }

  /**
   * Adds a program listener
   * @param programListener Program listener
   */
  public void addProgramListener(ProgramListener programListener) {
    programStack.addProgramListener(programListener);
  }

  /**
   * Updates the local program to sync with the database program
   * @param program New program stack
   */
  public void receiveProgramUpdates(List<CommandBlock> program) {
    programStack.receiveProgramUpdates(program);
  }

  private ScrollPane generateScrollPane(Node node) {
    ScrollPane programStackHolder = new ScrollPane();
    programStackHolder.setFitToWidth(true);
    programStackHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setContent(node);
    return programStackHolder;
  }

  private void addCommandBlock(String command) {
    programStack.addCommandBlock(command);
    programStack.notifyProgramListeners();
  }

}
