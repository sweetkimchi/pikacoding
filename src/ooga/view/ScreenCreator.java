package ooga.view;

import java.util.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.controller.FrontEndExternalAPI;
import ooga.view.animation.AnimationPane;

/**
 * 
 */
public class ScreenCreator {

    private Timeline timeline;
    FrontEndExternalAPI viewController;
    private Stage stage;
    private AnimationPane animationPane;
    /**
     * Default constructor
     */
    public ScreenCreator(FrontEndExternalAPI viewController, Stage stage) {
        this.viewController = viewController;
        this.stage = stage;

        animationPane = new AnimationPane(viewController);

        // debug statement
        System.out.println("Screen launched");
    }

    private void runSimulation() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

       //     updateTurtleStates();
            setAnimationSpeed();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setRate(300);
    }

    private void setAnimationSpeed() {
     //   timeline.setRate(userCommand.getAnimationSpeed());
    }

    public void updateCommandQueue(String commandType, List<Double> commandValues) {
        animationPane.updateCommandQueue(commandType, commandValues);
    }


    public void setPosition(double x, double y) {
        animationPane.setPosition(x,y);
    }

    public void setActiveAvatar(int avatarID) {
        animationPane.setActiveAvatar(avatarID);
    }
}