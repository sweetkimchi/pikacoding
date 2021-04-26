package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

/**
 * Drop is a type of Basic Command that directs the given avatar to drop the block it is currently
 * holding onto its current tile. If the avatar has no block to drop, or its current tile cannot
 * accept a block to be dropped, then the avatar will do nothing.
 *
 * @author Harrison Huang
 */
public class Drop extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public Drop(ElementInformationBundle elementInformationBundle,
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
    Tile currTile = getCurrTile(ID);
    if (currTile.canAddBlock()) {
      Block block = avatar.drop();
      if (block == null) {
        //if desired, handle error if the avatar is not holding a block
        //System.out.println("You are not holding a block!");
      } else {
        block.drop();
        sendBlockHeldUpdate(block);
      }
      currTile.add(block);
    } else {
      //if desired, handle error if the avatar cannot drop the block
      //System.out.println("You cannot drop here!");
    }

    incrementProgramCounterByOne(avatar);
  }
}
