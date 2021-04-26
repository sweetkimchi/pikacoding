package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

/**
 * PickUp is a type of Basic Command that directs the given avatar to pick up a block present in the
 * same tile as itself, if possible. If the avatar and the avatar's current tile both have blocks,
 * then performing a pick up will swap the two blocks. If there is no block to pick up, the avatar
 * will do nothing.
 *
 * @author Harrison Huang
 */
public class PickUp extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public PickUp(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * The execution behavior of the command on an Avatar given by an ID. The specific implementation
   * is to be overridden by the subclasses.
   *
   * @param ID The ID of the avatar to be commanded
   */
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
      //if desired, handle error when the tile has no block to be picked up
      //System.out.println("There is no block to be picked up!");
    }
    incrementProgramCounterByOne(avatar);

  }
}
