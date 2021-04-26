package ooga.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.view.factories.GUIElementFactory;
import ooga.view.factories.GUIElementInterface;

import java.util.ResourceBundle;

public class MatchIdSelector extends BorderPane {
  private static final String RESOURCES_FILE = ScreenCreator.RESOURCES + "MatchIdSelector";

  private ScreenCreator screenCreator;
  private ResourceBundle resourceBundle;
  private TextField matchInput;
  private GUIElementInterface GUIFactory;
  public MatchIdSelector(ScreenCreator sc) {
    screenCreator = sc;
    GUIFactory = new GUIElementFactory();
    int noNum = 0;
    resourceBundle = ResourceBundle.getBundle(RESOURCES_FILE);

    VBox matchBox = new VBox();
    matchBox.getStyleClass().add("type-screen");

    Label label = GUIFactory.makeLabel(resourceBundle, "instructions", "title", noNum);
    matchInput = new TextField();
    matchInput.setMaxWidth(Double.parseDouble(resourceBundle.getString("width")));
    Button enter = GUIFactory.makeButton(resourceBundle, e -> enter(), "enter", "default-button", "enter", noNum);

    matchBox.getChildren().addAll(label, matchInput, enter);
    this.setCenter(matchBox);
  }

  public void enter() {
    try {
      int matchId = Integer.parseInt(matchInput.getText());
      screenCreator.setMatchId(matchId);
      screenCreator.loadGameTypeSelector();
      System.out.println(matchId);
    } catch (Exception error) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText(resourceBundle.getString("idError"));
      alert.showAndWait();
    }
  }
}
