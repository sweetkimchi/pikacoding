package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;

public abstract class MathematicalCommands extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public MathematicalCommands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = getAvatar(ID);
    Tile currTile = getCurrTile(ID);
    if (currTile.getBlock() instanceof DataCube tileCube && avatar
        .getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = calculateNewDisplayNum(avatarCube.getDisplayNum(), tileCube.getDisplayNum());
      avatarCube.setDisplayNum(newDisplayNum);
      System.out.println("Newly added NUMBER: " + newDisplayNum);

      sendDataCubeNumUpdate(avatarCube);
    } else {
      //TODO: throw error to handler, change message to be specific for operation?
      System.out.println("Cannot add blocks!");
    }

    incrementProgramCounterByOne(avatar);
  }

  public abstract int calculateNewDisplayNum(int avatarCubeNum, int tileCubeNum);
}