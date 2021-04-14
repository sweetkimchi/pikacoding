package ooga.model.commands;

import java.util.*;
import ooga.model.Direction;
import ooga.model.grid.GameGrid;
import ooga.model.player.Avatar;

/**
 * @author Ji Yun Hyo
 */
public class Step extends BasicCommands {

    private final GameGrid gameGrid;
    private final Direction direction;

    /**
     * Default constructor
     */
    public Step(GameGrid gameGrid, Direction direction) {
        this.gameGrid = gameGrid;
        this.direction = direction;
    }

    /**
     * Executes the command on an Avatar.
     *
     * @param avatar The avatar upon which to execute the command
     */
    @Override
    public void execute(Avatar avatar) {
        gameGrid.step(avatar.getId(), direction);

    }
}