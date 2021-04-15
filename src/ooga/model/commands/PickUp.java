package ooga.model.commands;

import java.util.Map;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

public class PickUp extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public PickUp(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    //TODO: determine if pick up should take in parameter, now defaults to the same tile
    //Direction direction = getDirection(getParameters().get("direction"));
    Direction direction = Direction.SELF;
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    int newX = currX + direction.getXDel();
    int newY = currY + direction.getYDel();
    Tile tileToPickUpFrom = getElementInformationBundle().getTile(newX,newY);
    if (tileToPickUpFrom.hasBlock()) {
      Block temp = null;
      if (avatar.hasBlock()) {
        temp = avatar.drop();
        temp.drop();
      }
      Block block = tileToPickUpFrom.getBlock();
      System.out.println("Pickup " + block.getId());

      avatar.pickUp(block);

      getElementInformationBundle().getModelController().updateBlock(block.getId(), avatar.hasBlock());
      tileToPickUpFrom.removeBlock();
      tileToPickUpFrom.add(temp);
      if (temp != null) getElementInformationBundle().getModelController().updateBlock(temp.getId(), temp.isHeld());
      avatar.getHeldItem().pickUp(avatar.getId());
    } else {
      //TODO: throw error to handler
      System.out.println("There is no block to be picked up!");
    }
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);

  }
}
