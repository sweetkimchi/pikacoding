package ooga.view.level.codearea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
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

  public CodeArea() {
    commandBank = new CommandBank(this::addCommandBlock);
    programStack = new ProgramStack();
    ScrollPane programStackHolder = initializeProgramStackHolder();
    this.add(commandBank, 0, 0);
    this.add(programStackHolder, 1, 0);
    ResourceBundle sizeProperties = ResourceBundle
        .getBundle(ScreenCreator.RESOURCES + CODEAREA_PROPERTIES);
    double commandBankWidth = Double.parseDouble(sizeProperties.getString("CommandBankWidth"));
    sizeProperties = ResourceBundle.getBundle(ScreenCreator.RESOURCES + LevelView.LEVEL_PROPERTIES);
    double programWidth =
        Double.parseDouble(sizeProperties.getString("CodeAreaWidth")) - commandBankWidth;
    commandBank.setPrefWidth(commandBankWidth);
    programStack.setPrefWidth(programWidth);
  }

  public void setLineIndicators(Map<Integer, Integer> lineNumbers) {
    programStack.setLineIndicators(lineNumbers);
  }

  public List<CommandBlock> getProgram() {
    return programStack.getProgram();
  }

  public void setAvailableCommands(AvailableCommands availableCommands) {
    commandBank.addCommands(availableCommands.getCommandNames());
    programStack.setAvailableCommands(availableCommands);
  }

  public void addProgramListener(ProgramListener programListener) {
    programStack.addProgramListener(programListener);
  }

  private ScrollPane initializeProgramStackHolder() {
    ScrollPane programStackHolder = new ScrollPane();
    programStackHolder.setFitToWidth(true);
    programStackHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setContent(programStack);
    return programStackHolder;
  }

  private void addCommandBlock(String command) {
    programStack.addCommandBlock(command);
  }

}
