package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public abstract class ConditionalCommands extends Commands {

    /**
     * Default constructor
     */
    public ConditionalCommands(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }


}