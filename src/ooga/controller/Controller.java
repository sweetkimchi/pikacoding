package ooga.controller;

import javafx.stage.Stage;

/**
 *
 */
public class Controller {

  FrontEndExternalAPI viewController;
  BackEndExternalAPI modelController;

  /**
   * Default constructor
   */
  public Controller(Stage stage) {
    modelController = new ModelController();
    viewController = new ViewController(stage);
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);

    viewController.loadStartMenu();
  }

}