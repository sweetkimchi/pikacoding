package ooga.controller;

import java.util.*;
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
        // debug statement
        System.out.println("modelController made");
        System.out.println("viewController made");
        modelController = new ModelController();
        viewController = new ViewController(stage);
        viewController.setModelController(modelController);
        modelController.setViewController(viewController);
    }

}