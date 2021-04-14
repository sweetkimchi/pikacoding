package ooga.model.commands;

import java.util.*;
import ooga.model.grid.ElementInformationBundle;

/**
 * @author Ji Yun Hyo
 */
public abstract class LoopCommands extends Commands {

    /**
     * Default constructor
     */
    public LoopCommands(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

}