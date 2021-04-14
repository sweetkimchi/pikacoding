package ooga.view.level;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ooga.view.ScreenCreator;

public class ControlPanel extends GridPane {

  private static final String BUTTON_IMAGES = "ControlPanelButtons";
  private static final int ICON_SIZE = 30; //TODO: put in properties file
  private Map<String, Button> buttons;
  private ResourceBundle buttonImages;
  private int col = 0;
  private Slider slider;

  public ControlPanel() {
    this.getStyleClass().add("control-panel");
    buttonImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES + BUTTON_IMAGES);
    Object[] buttonNames = buttonImages.keySet().toArray();
    Arrays.sort(buttonNames);
    buttons = new HashMap<>();
    makeButtons(buttonNames);
    makeSlider();
  }

  public void setButtonAction(String buttonName, EventHandler<ActionEvent> eventHandler) {
    buttons.get(buttonName).setOnAction(eventHandler);
  }

  private void makeSlider() {
    slider = new Slider(10, 100,25);
    this.add(slider, col, 0);
  }

  private void makeButtons(Object[] buttonNames) {
    for (Object o : buttonNames) {
      Button button = new Button();
      buttons.put(o.toString(), button);
      button.setGraphic(setIcon(buttonImages.getString(o.toString())));
      button.getStyleClass().add(o.toString());
      button.setId(o.toString() + "-button");
      this.add(button, col, 0);
      col++;
    }
  }

  private ImageView setIcon(String icon) {
    ImageView iconView = new ImageView(new Image(icon));
    iconView.setFitWidth(ICON_SIZE);
    iconView.setFitHeight(ICON_SIZE);
    return iconView;
  }

  public double getSliderSpeed() {
    return slider.getValue();
  }
}
