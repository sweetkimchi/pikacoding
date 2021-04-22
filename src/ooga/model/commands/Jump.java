package ooga.model.commands;

import java.util.*;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class Jump extends ControlFlowCommands {


    /**
     * Default constructor
     */
    public Jump(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

    @Override
    public void execute(int ID) {
        Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
        avatar.setProgramCounter(Integer.parseInt(getParameters().get("destination")));
    }
}