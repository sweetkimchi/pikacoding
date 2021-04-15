package ooga.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StartMenu extends BorderPane {
  private static final String CSS = ScreenCreator.RESOURCES.replace(".", "/") + "css/";
  private String styleSheet = "default.css";
  private static final String CSS_POSSIBILITIES = ScreenCreator.RESOURCES + "CSSPossibilities";

  public StartMenu(EventHandler<ActionEvent> startAction) {
    this.getStylesheets().add(CSS + styleSheet);
    VBox center = new VBox();
    center.getStyleClass().add("start-screen");
    Label title = new Label("PikaCoding");
    title.getStyleClass().add("title");

    Button startButton = new Button("Start Game");
    startButton.setOnAction(startAction);
    startButton.setId("start-button");
    center.getChildren().addAll(title, startButton);

    ComboBox<String> chooseStyleSheet = makeComboBox();;
    center.getChildren().add(chooseStyleSheet);
    this.setCenter(center);
  }

  private ComboBox<String> makeComboBox() {
    ComboBox<String> comboBox = new ComboBox<>();
    ResourceBundle cssPossibilities = ResourceBundle.getBundle(CSS_POSSIBILITIES);
    Object[] keys = cssPossibilities.keySet().toArray();
    List<String> allCSS = new ArrayList<>();
    for (Object o : keys) {
      allCSS.add(cssPossibilities.getString(o.toString()));
    }
    comboBox.getItems().addAll(allCSS);
    comboBox.setValue("default");
    comboBox.setOnAction(event -> updateStyleSheet(comboBox.getValue()));
    return comboBox;
  }

    private void updateStyleSheet(Object value) {
    this.getStylesheets().remove(CSS + styleSheet);
    styleSheet = value.toString() + ".css";
    this.getStylesheets().add(CSS + styleSheet);
  }

  public String getStyleSheet() {
    return CSS + styleSheet;
  }

}
