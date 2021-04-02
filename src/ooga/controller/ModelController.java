package ooga.controller;

import java.util.*;
import javax.lang.model.util.Elements;
import ooga.model.CommandExecutor;
import ooga.view.CommandBlock;

/**
 * 
 */
public class ModelController implements BackEndExternalAPI {

    FrontEndExternalAPI viewController;

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
     * Passes in the commands to be parsed and executed
     *
     * @param commandBlocks List of individual command blocks derived from the blocks in the
     *                      CodeBuilderArea
     */
    @Override
    public void parseAndExecuteCommands(List<CommandBlock> commandBlocks) {
        CommandExecutor commandExecutor = new CommandExecutor(commandBlocks);
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

    }
}