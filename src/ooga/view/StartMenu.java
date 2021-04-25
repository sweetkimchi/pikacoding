package ooga.view;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartMenu extends BorderPane {
  private static final String CSS = (ScreenCreator.class.getPackageName() + ".resources.").replace(".", "/") + "css/";
  private String styleSheet = "default.css";
  private static final String CSS_POSSIBILITIES = ScreenCreator.RESOURCES + "CSSPossibilities";
  private static final String LANGUAGE_POSSIBILITIES = ScreenCreator.RESOURCES + "LanguagePossibilities";
  private static final String START_MENU_STRINGS = ScreenCreator.RESOURCES + "StartMenu";
  private ResourceBundle startMenuResources;

  public StartMenu(EventHandler<ActionEvent> startAction) {
    this.getStylesheets().add(CSS + styleSheet);
    startMenuResources = ResourceBundle.getBundle(START_MENU_STRINGS);
    VBox center = new VBox();
    center.getStyleClass().add("start-screen");
    Label title = new Label("PikaCoding");
    title.getStyleClass().add("title");

    Button startButton = new Button("Start Game");
    startButton.setOnAction(startAction);
    startButton.setId(ScreenCreator.idsForTests.getString("startButton"));
    startButton.getStyleClass().add("default-button");
    center.getChildren().addAll(title, startButton);

    ComboBox<String> chooseStyleSheet = makeComboBox(CSS_POSSIBILITIES, "css");
    ComboBox<String> chooseLanguage = makeComboBox(LANGUAGE_POSSIBILITIES, "lang");
    center.getChildren().addAll(chooseStyleSheet, chooseLanguage);
    this.setCenter(center);
  }

  private ComboBox<String> makeComboBox(String file, String type) {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(ScreenCreator.idsForTests.getString(type + "ComboBox"));
    ResourceBundle comboBoxPossibilities = ResourceBundle.getBundle(file);
    Object[] keys = comboBoxPossibilities.keySet().toArray();
    List<String> allPossibilities = new ArrayList<>();
    for (Object o : keys) {
      allPossibilities.add(comboBoxPossibilities.getString(o.toString()));
    }
    comboBox.getItems().addAll(allPossibilities);
    comboBox.setValue(startMenuResources.getString(type + "Default"));
    comboBox.setOnAction(event -> reflectionMethod(comboBox.getValue().toString(), type + "Action"));
    return comboBox;
  }

  private void reflectionMethod(String value, String key) {
    try {
      String methodName = startMenuResources.getString(key);
      Method m = StartMenu.this.getClass().getDeclaredMethod(methodName, String.class);
      m.invoke(StartMenu.this, value);
    }
    catch (Exception e) {
      new Alert(Alert.AlertType.ERROR);
    }

  }

  // TODO: write code to support changing languages
  private void updateLanguage(String lang) {

  }

  private void updateStyleSheet(String css) {
    this.getStylesheets().remove(CSS + styleSheet);
    styleSheet = css + ".css";
    this.getStylesheets().add(CSS + styleSheet);
  }

  public String getStyleSheet() {
    return CSS + styleSheet;
  }

}
