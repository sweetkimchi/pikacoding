package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;

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
    Avatar avatar = getAvatar(ID);
    Tile currTile = getCurrTile(ID);
    if (currTile.canAddBlock()) {
      Block block = avatar.drop();
      if (block == null) {
        //TODO: throw error to handler
        System.out.println("You are not holding a block!");
      } else {
        block.drop();
        sendBlockHeldUpdate(block);
      }
      currTile.add(block);
    } else {
      //TODO: throw error to handler
      System.out.println("You cannot drop here!");

    }

    incrementProgramCounterByOne(avatar);
  }
}
