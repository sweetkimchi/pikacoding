package ooga.view.level.codearea;

import java.util.List;
import javafx.scene.layout.VBox;

public class CommandBank extends VBox {

  public CommandBank(List<String> availableCommands) {
    this.setSpacing(4);
    availableCommands.forEach(command -> {
      CommandBlockOption option = new CommandBlockOption(command, e -> {
        System.out.println("new command");
      });
      option.getRectangle().widthProperty().bind(this.widthProperty());
      this.getChildren().add(option);
    });
  }

}
