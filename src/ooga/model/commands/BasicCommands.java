package ooga.model.commands;

import java.util.*;
import ooga.model.grid.ElementInformationBundle;

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

}