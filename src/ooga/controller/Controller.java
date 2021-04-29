package ooga.controller;

import javafx.stage.Stage;

/**
 * This class is the class Main.java uses to set up the controllers for initiating the software.
 * viewController starts the GUI and modelController starts the backend model.
 * @author Ji Yun Hyo
 */
public class Controller {

  FrontEndExternalAPI viewController;
  BackEndExternalAPI modelController;

  /**
   * Default constructor that sets up the controller
   */
  public Controller(Stage stage) {
    modelController = new ModelController();
    viewController = new ViewController(stage);
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);

    viewController.loadStartMenu();
  }

}