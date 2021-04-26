package ooga.view.level.codearea;

import java.util.Set;
import java.util.function.Consumer;
import javafx.scene.layout.VBox;

/**
 * Displays a list of available commands, allowing the user to click on them to add them to the
 * program stack.
 *
 * @author David Li.
 */
public class CommandBank extends VBox {

  private final Consumer<String> clickOptionAction;

  public CommandBank(Consumer<String> clickOptionAction) {
    this.setId("CommandBank");
    this.setSpacing(6);
    this.clickOptionAction = clickOptionAction;
  }

  public void addCommands(Set<String> commands) {
    commands.forEach(command -> {
      CommandBlockOption option = new CommandBlockOption(command,
          e -> {
            clickOptionAction.accept(command);
          });
      this.getChildren().add(option);
    });
  }

  public void addCommandsOtherPlayer(Set<String> commands) {
    commands.forEach(command -> {
      CommandBlockOption option = new CommandBlockOption(command,
          e -> {
          });
      option.getStyleClass().add("command-block-option-disabled");
      this.getChildren().add(option);
    });
  }
}
