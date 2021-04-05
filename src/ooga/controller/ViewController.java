package ooga.controller;

import java.util.*;
import javafx.stage.Stage;
import ooga.model.grid.gridData.BoardState;
import ooga.model.player.Element;
import ooga.view.level.codearea.CommandBlock;
import ooga.view.ScreenCreator;
import ooga.view.level.LevelView;

/**
 * @author Ji Yun Hyo
 */
public class ViewController implements FrontEndExternalAPI {

    BackEndExternalAPI modelController;
    ScreenCreator screenCreator;
    LevelView levelView;

    /**
     * Default constructor
     * @param stage
     */
    public ViewController(Stage stage) {
        screenCreator = new ScreenCreator(this, stage);
        levelView = screenCreator.getLevelView();
    }
    /**
     *
     * sets the model controller to set up the line of communication from/to the backend
     *
     * @param modelController BackEndExternalAPI
     */
    @Override
    public void setModelController(BackEndExternalAPI modelController) {
        this.modelController = modelController;
    }

    /**
     * Sets the view board to contain a new level. Instantiates all the elements of the grid,
     * including the dimensions and initial locations of humans and objects.
     *
     * @param boardState The initial state of the board
     */
    @Override
    public void setBoard(BoardState boardState) {

    }

    /**
     * Updates and individual sprite (avatars, block)
     *
     * @param id         Id of the sprite to be updated
     * @param spriteData Representation of element of the game
     */
    @Override
    public void updateSprite(int id, Element spriteData) {

    }

    /**
     * Sets the position of the sprite
     *
     * @param x
     * @param y
     * @param id
     */
    @Override
    public void setPosition(double x, double y, int id) {
        levelView.setPosition(x,y);
    }

    @Override
    public void setActiveAvatar(int avatarID) {
        levelView.setActiveAvatar(avatarID);
    }

    /**
     * Passes in the commands to be parsed and executed
     *
     * @param commandBlocks List of individual command blocks derived from the blocks in the
     *                      CodeBuilderArea
     */
    @Override
    public void parseAndExecuteCommands(List<CommandBlock> commandBlocks) {
        modelController.parseAndExecuteCommands(commandBlocks);
    }
}