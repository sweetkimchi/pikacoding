package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

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
    Avatar avatar = getAvatar(ID);
    Tile tileToPickUpFrom = getCurrTile(ID);
    if (tileToPickUpFrom.hasBlock()) {
      Block temp = null;
      if (avatar.hasBlock()) {
        temp = avatar.drop();
        temp.drop();
      }
      Block block = tileToPickUpFrom.getBlock();
      System.out.println("Pickup " + block.getId());

      avatar.pickUp(block);

      tileToPickUpFrom.removeBlock();
      tileToPickUpFrom.add(temp);
      if (temp != null) {
        sendBlockHeldUpdate(temp);
      }
      avatar.getHeldItem().pickUp();
      sendBlockHeldUpdate(block);
    } else {
      //TODO: throw error to handler
      System.out.println("There is no block to be picked up!");
    }
    incrementProgramCounterByOne(avatar);

  }
}
