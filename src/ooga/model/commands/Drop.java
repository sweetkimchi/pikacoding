package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

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
