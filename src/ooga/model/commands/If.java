package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class If extends ConditionalCommands {

    /**
     * Default constructor
     */
    public If(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

    /**
     * Executes the command on an Avatar.
     *
     * @param avatar The avatar upon which to execute the command
     */
    public void execute(Avatar avatar) {

    }

    @Override
    public void execute(int ID) {

    }
}