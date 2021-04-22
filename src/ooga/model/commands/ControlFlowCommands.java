package ooga.model.commands;

import java.util.*;
import ooga.model.grid.ElementInformationBundle;

/**
 * @author Ji Yun Hyo
 */
public abstract class ControlFlowCommands extends Commands {

    /**
     * Default constructor
     */
    public ControlFlowCommands(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

}