package ooga.view.level.codearea;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import ooga.model.commands.AvailableCommands;
import ooga.view.ScreenCreator;
import ooga.view.level.LevelView;
import org.jetbrains.annotations.NotNull;

/**
 * Area of the level view that displays the command bank and program stack.
 *
 * @author David Li
 */
public class CodeArea extends GridPane {

  private static final String CODEAREA_PROPERTIES = "CodeArea";

  private CommandBank commandBank;
  private ProgramStack programStack;

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
    double programWidth = Double.parseDouble(sizeProperties.getString("CodeAreaWidth")) - commandBankWidth;
    commandBank.setPrefWidth(commandBankWidth);
    programStack.setPrefWidth(programWidth);
  }

  private ScrollPane initializeProgramStackHolder() {
    ScrollPane programStackHolder = new ScrollPane();
    programStackHolder.setFitToWidth(true);
    programStackHolder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    programStackHolder.setContent(programStack);
    return programStackHolder;
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
