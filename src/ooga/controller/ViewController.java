package ooga.controller;

import java.util.*;
import javafx.stage.Stage;
import ooga.model.commands.AvailableCommands;
import ooga.model.grid.gridData.GameGridData;
import ooga.model.grid.gridData.InitialState;
import ooga.model.player.AvatarData;
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
     * @param gameGridData The tile information of the grid
     * @param initialState The initial state of the board sprites
     */
    @Override
    public void setBoard(GameGridData gameGridData, InitialState initialState) {
        levelView.initializeBoard(gameGridData, initialState);
    }

    @Override
    public void setAvailableCommands(AvailableCommands availableCommands) {
        levelView.setAvailableCommands(availableCommands);
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
        levelView.setPosition(x,y, id);
    }

    @Override
    public void setActiveAvatar(int avatarID) {
        levelView.setActiveAvatar(avatarID);
    }

    @Override
    public void loadStartMenu() {
        screenCreator.loadStartMenu();
    }

    @Override
    public void initializeLevel(int level) {
        screenCreator.initializeLevelView();
        levelView = screenCreator.getLevelView();
        modelController.initializeLevel(level);
    }

    @Override
    public void updateAvatarPositions(int id, int xCoord, int yCoord) {
        levelView.updateAvatarPositions(id, xCoord,yCoord);
    }

    @Override
    public void updateFrontEndElements(Map<String, AvatarData> updates) {
        levelView.updateFrontEndElements(updates);
    }

    @Override
    public void declareEndOfAnimation() {
        levelView.declareEndOfAnimation();
    }

    @Override
    public void setScore(int score) {
        //TODO: edit as you see fit (screenCreator vs levelView)
        screenCreator.setScore(score);
    }

    /**
     * sets the line number for the avatar
     *
     * @param lineUpdates
     */
    @Override
    public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
        levelView.setLineIndicators(lineUpdates);
    }

    /**
     * Passes in the commands to be parsed
     *
     * @param commandBlocks List of individual command blocks derived from the blocks in the
     *                      CodeBuilderArea
     */
    @Override
    public void parseCommands(List<CommandBlock> commandBlocks) {
        modelController.parseCommands(commandBlocks);
    }

    /**
     * Runs the next command in the command queue
     */
    @Override
    public void runNextCommand() {
        modelController.runNextCommand();
    }

}