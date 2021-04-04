package ooga.view.level.codearea;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.VBox;

public class CommandBank extends VBox {

  public CommandBank(List<String> availableCommands, Consumer<String> clickOptionAction) {
    this.setId("CommandBank");
    this.setSpacing(4);
    availableCommands.forEach(command -> {
      CommandBlockOption option = new CommandBlockOption(command, e -> {
        clickOptionAction.accept(command);
      });
      option.getRectangle().widthProperty().bind(this.widthProperty());
      this.getChildren().add(option);
    });
  }

}
