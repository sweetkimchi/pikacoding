package ooga.view.level.codearea;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.VBox;

/**
 * Displays a list of available commands, allowing the user to click on them to add them to the
 * program stack.
 *
 * @author David Li.
 */
public class CommandBank extends VBox {

  public CommandBank(List<String> availableCommands, Consumer<String> clickOptionAction) {
    this.setId("CommandBank");
    this.setSpacing(4);
    availableCommands.forEach(command -> {
      CommandBlockOption option = new CommandBlockOption(command,
          e -> clickOptionAction.accept(command));
      this.getChildren().add(option);
    });
  }

}
