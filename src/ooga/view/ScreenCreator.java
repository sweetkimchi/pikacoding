package ooga.view;

import java.util.*;
import javafx.stage.Stage;
import ooga.controller.FrontEndExternalAPI;

/**
 * 
 */
public class ScreenCreator {

    FrontEndExternalAPI viewController;
    private Stage stage;
    /**
     * Default constructor
     */
    public ScreenCreator(FrontEndExternalAPI viewController, Stage stage) {
        this.viewController = viewController;
        this.stage = stage;

        // debug statement
        System.out.println("Screen launched");
    }


}