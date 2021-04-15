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
    center.getChildren().addAll(title, startButton);

    ComboBox<String> chooseStyleSheet = new ComboBox<>();
    ResourceBundle cssPossibilities = ResourceBundle.getBundle(CSS_POSSIBILITIES);
    Object[] keys = cssPossibilities.keySet().toArray();
    List<String> allCSS = new ArrayList<>();
    for (Object o : keys) {
      allCSS.add(cssPossibilities.getString(o.toString()));
    }
    chooseStyleSheet.getItems().addAll(allCSS);
    chooseStyleSheet.setValue("default");
    center.getChildren().add(chooseStyleSheet);
    chooseStyleSheet.setOnAction(event -> updateStyleSheet(chooseStyleSheet.getValue()));
    this.setCenter(center);
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
