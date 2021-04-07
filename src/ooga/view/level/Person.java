package ooga.view.level;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Person {
  private static final String personImage = "PikachuAvatar.gif"; // TODO: put in resource file or get passed
  private int row;
  private int col;
  private double width;
  private double height;
  private GridPane grid;
  private ImageView person;
  public Person(int r, int c, double w, double h, GridPane root) {
    row = r;
    col = c;
    width = w;
    height = h;
    grid = root;
    makePerson();
  }

  private void makePerson() {
    person = new ImageView(new Image(personImage));
    person.setFitWidth(width);
    person.setFitHeight(height);
    grid.add(person, col, row);
  }

  public void movePerson(double r, double c) {
    person.setTranslateX(r + person.getTranslateX());
    person.setTranslateY(c + person.getTranslateY());
  }
}
