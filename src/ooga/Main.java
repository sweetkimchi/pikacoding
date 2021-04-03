package ooga;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications. The start method is called after the init
     * method has returned, and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which the application scene
     *                     can be set. Applications may create other stages, if needed, but they will
     *                     not be primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(primaryStage);
    }
}
