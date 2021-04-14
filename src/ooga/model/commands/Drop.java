package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.Player;

public class Drop extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Drop(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    Tile currTile = getElementInformationBundle().getTile(currX,currY);
    if (currTile.canAddBlock()) {
      Block block = avatar.drop();
      if (block == null) {
        //TODO: throw error to handler
        System.out.println("You are not holding a block!");
      }else{
        getElementInformationBundle().getModelController().updateBlock(block.getId(),
            avatar.hasBlock());
      }
      currTile.add(block);
    } else {
      //TODO: throw error to handler
      System.out.println("You cannot drop here!");

    }

    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }
}
