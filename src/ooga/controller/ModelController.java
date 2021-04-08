package ooga.controller;

import java.util.*;
import javax.lang.model.util.Elements;
import ooga.model.CommandExecutor;
import ooga.model.grid.gridData.BoardState;
import ooga.model.parser.InitialConfigurationParser;
import ooga.model.player.AvatarData;
import ooga.view.level.codearea.CommandBlock;

/**
 * 
 */
public class ModelController implements BackEndExternalAPI {

    private FrontEndExternalAPI viewController;
    private CommandExecutor commandExecutor;
    private InitialConfigurationParser initialConfigurationParser;

    /**
     * Default constructor
     */
    public ModelController() {
    }

    /**
     * 
     */
    public void parseAndExecute() {
        // TODO implement here
    }

    /**
     * sets the view controller to set up the line of communication from/to the backend
     *
     * @param viewController FrontEndExternalAPI
     */
    @Override
    public void setViewController(FrontEndExternalAPI viewController) {
        this.viewController = viewController;
    }

    /**
     * Passes in the commands to be parsed
     *
     * @param commandBlocks List of individual command blocks derived from the blocks in the
     *                      CodeBuilderArea
     */
    @Override
    public void parseCommands(List<CommandBlock> commandBlocks) {
        //TODO: delete after debugging. Initializing level for testing purposes
        initialConfigurationParser = new InitialConfigurationParser(1);

        commandExecutor = new CommandExecutor(commandBlocks, this, initialConfigurationParser.getInitialState());
    }

    /**
     * Runs the next command in the command queue
     */
    @Override
    public void runNextCommand() {
        commandExecutor.runNextCommand();
        System.out.println("Running next command");
    }

    /**
     * Gets the list of changed states in order to update the frontend.
     *
     * @return The list of changed states in the grid
     */
    @Override
    public List<Elements> getChangedStates() {
        return null;
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
        viewController.setPosition(x,y,id);
    }

    @Override
    public void setBoard(BoardState board) {

    }

    /**
     * All commands have reached the end and no more to be executed
     */
    @Override
    public void declareEndOfAnimation() {

        viewController.declareEndOfAnimation();
    }

    /**
     * initializes the level
     *
     * @param level integer indicating the level
     * @return BoardState object with level information
     */
    @Override
    public BoardState initializeLevel(int level) {
        initialConfigurationParser = new InitialConfigurationParser(level);
        viewController.setAvailableCommands(initialConfigurationParser.getAvailableCommands());
        return initialConfigurationParser.getInitialState();
    }

    @Override
    public void updateAvatarPositions(int id, int xCoord, int yCoord) {
        viewController.updateAvatarPositions(id, xCoord, yCoord);
    }

    @Override
    public void setAvatarIDForUpdate(int id) {

    }

    @Override
    public void updateFrontEndElements(Map<String, AvatarData> updates) {
        viewController.updateFrontEndElements(updates);
    }
}