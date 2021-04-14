package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.Element;
import ooga.model.player.Player;

/**
 * @author Ji Yun Hyo
 */
public class Step extends BasicCommands {


    /**
     * Default constructor
     */
    public Step(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
        super(elementInformationBundle, parameters);
    }

    /**
     * Executes the command on an Avatar.
     *
     */
    @Override
    public void execute(int ID) {
        Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
        Direction direction = getDirection(getParameters().get("direction"));
        assert avatar != null;
        int currX = avatar.getXCoord();
        int currY = avatar.getYCoord();
        int newX = currX + direction.getXDel();
        int newY = currY + direction.getYDel();

        System.out.println(getElementInformationBundle().getTile(newX, newY).getStructure());

        if (getElementInformationBundle().getTile(newX, newY).canAddAvatar()) {
            getElementInformationBundle().getTile(newX, newY).add(avatar);
            getElementInformationBundle().getTile(currX, currY).removeAvatar();
            avatar.setXCoord(newX);
            avatar.setYCoord(newY);
        } else {
            //TODO: throw error to handler?
            System.out.println("The avatar cannot step here!");
        }

        getElementInformationBundle().getModelController().updateAvatarPositions(avatar.getId(), avatar.getXCoord(), avatar.getYCoord());


        avatar.setProgramCounter(avatar.getProgramCounter() + 1);

    }
}