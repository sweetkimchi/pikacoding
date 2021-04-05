package ooga.view.level.codearea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * A single command option in the command bank. Adds the command to the program stack when clicked.
 *
 * @author David Li
 */
public class CommandBlockOption extends Button {

  public CommandBlockOption(String command, EventHandler<ActionEvent> eventHandler) {
    this.getStyleClass().add("command-block-option");
    this.setText(command);
    this.setOnAction(eventHandler);
  }

}
