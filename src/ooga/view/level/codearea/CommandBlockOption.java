package ooga.view.level.codearea;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A single command option in the command bank.
 * Adds the command to the program stack when clicked.
 *
 * @author David Li
 */
public class CommandBlockOption extends StackPane {

  private Label label;
  private Rectangle rectangle;

  public CommandBlockOption(String command, EventHandler<MouseEvent> eventHandler) {
    label = new Label(command);
    rectangle = new Rectangle();
    initializeRectangle(eventHandler);
    StackPane.setAlignment(label, Pos.CENTER_LEFT);
    this.getChildren().addAll(label, rectangle);
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  private void initializeRectangle(EventHandler<MouseEvent> eventHandler) {
    rectangle.heightProperty().bind(label.heightProperty());
    rectangle.setFill(Color.GREEN);
    rectangle.setOpacity(0.4);
    rectangle.setOnMouseEntered(event -> {
      getScene().setCursor(Cursor.HAND);
    });
    rectangle.setOnMouseExited(event -> {
      getScene().setCursor(Cursor.DEFAULT);
    });
    rectangle.setOnMouseClicked(eventHandler);
  }

}
