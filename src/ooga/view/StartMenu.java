package ooga.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartMenu extends BorderPane {
  private static final String CSS = (ScreenCreator.class.getPackageName() + ".resources.").replace(".", "/") + "css/";
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
    startButton.setId(ScreenCreator.idsForTests.getString("startButton"));
    startButton.getStyleClass().add("default-button");
    center.getChildren().addAll(title, startButton);

    ComboBox<String> chooseStyleSheet = makeComboBox();
    center.getChildren().add(chooseStyleSheet);
    this.setCenter(center);
  }

  private ComboBox<String> makeComboBox() {
    ComboBox<String> cssComboBox = new ComboBox<>();
    cssComboBox.setId(ScreenCreator.idsForTests.getString("cssComboBox"));
    ResourceBundle cssPossibilities = ResourceBundle.getBundle(CSS_POSSIBILITIES);
    Object[] keys = cssPossibilities.keySet().toArray();
    List<String> allCSS = new ArrayList<>();
    for (Object o : keys) {
      allCSS.add(cssPossibilities.getString(o.toString()));
    }
    cssComboBox.getItems().addAll(allCSS);
    cssComboBox.setValue("default");
    cssComboBox.setOnAction(event -> updateStyleSheet(cssComboBox.getValue()));
    return cssComboBox;
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
