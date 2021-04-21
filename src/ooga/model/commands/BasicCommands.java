package ooga.model.commands;

import java.util.*;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.Element;

/**
 * @author Ji Yun Hyo
 */
public abstract class BasicCommands extends Commands {

    /**
     * Default constructor
     */
    public BasicCommands(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

    public void incrementProgramCounterByOne(Avatar avatar) {
        avatar.setProgramCounter(avatar.getProgramCounter() + 1);
    }

    public void sendAvatarPositionUpdate(Avatar avatar) {
        getElementInformationBundle().getModelController().updateAvatarPositions(avatar.getId(), avatar.getXCoord(), avatar.getYCoord());
    }

    public void sendBlockPositionUpdate(Block block) {
        getElementInformationBundle().getModelController().updateBlockPositions(block.getId(), block.getXCoord(), block.getYCoord());
    }

    public void sendBlockHeldUpdate(Block block) {
        getElementInformationBundle().getModelController().updateBlock(block.getId(), block.isHeld());
    }

    public void sendElementIsActiveUpdate(Element element) {
        // TODO: tell front end that the element is inactive (i.e. fell in a hole)
    }

}