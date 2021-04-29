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

/**
 * Creates pane that contains all the necessary buttons to control the game.
 * Includes play, pause, step, and reset buttons.
 * @author Kathleen Chen
 * @author David Li
 * @author Ji Yun Hyo
 */

public class ControlPanel extends GridPane {
  private static final String CONTROL_PANEL_PROPERTIES = "ControlPanel";
  private Map<String, Button> buttons;
  private ResourceBundle buttonImages;
  private ResourceBundle controlPanelResources;
  private int col = 0;
  private Slider slider;

  /**
   * Main constructor.
   */
  public ControlPanel() {
    this.getStyleClass().add("control-panel");
    controlPanelResources = ResourceBundle.getBundle(ScreenCreator.RESOURCES + CONTROL_PANEL_PROPERTIES);
    buttonImages = ResourceBundle.getBundle(ScreenCreator.RESOURCES +
            controlPanelResources.getString("buttonImages"));
    Object[] buttonNames = buttonImages.keySet().toArray();
    Arrays.sort(buttonNames);
    buttons = new HashMap<>();
    makeButtons(buttonNames);
    makeSlider();
  }

  /**
   * Returns the speed of the slider that controls animations.
   * @return doulbe slider speed
   */
  public double getSliderSpeed() {
    return slider.getValue();
  }

  /**
   * Set's a particular button's action based on the EventHandler.
   * @param buttonName String of the name of the button
   * @param eventHandler EventHandler that is the button's setOnAction action
   */
  public void setButtonAction(String buttonName, EventHandler<ActionEvent> eventHandler) {
    buttons.get(buttonName).setOnAction(eventHandler);
  }

  private void makeSlider() {
    slider = new Slider(10, 100,25);
    slider.setId(ScreenCreator.idsForTests.getString("slider"));
    this.add(slider, col, 0);
  }

  private void makeButtons(Object[] buttonNames) {
    for (Object o : buttonNames) {
      Button button = new Button();
      buttons.put(o.toString(), button);
      button.setGraphic(setIcon(buttonImages.getString(o.toString())));
      button.getStyleClass().add(o.toString());
      button.setId(ScreenCreator.idsForTests.getString(o.toString()));
      this.add(button, col, 0);
      col++;
    }
  }

  private ImageView setIcon(String icon) {
    ImageView iconView = new ImageView(new Image(icon));
    iconView.setFitWidth(Double.parseDouble(controlPanelResources.getString("iconSize")));
    iconView.setFitHeight(Double.parseDouble(controlPanelResources.getString("iconSize")));
    return iconView;
  }

}
