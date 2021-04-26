package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;

/**
 * Mathematical Commands are a subclass of Basic Commands that set a value to the datacube held by
 * the Avatar based on the current value of the avatar datacube or the value of the datacube on the
 * same tile that the Avatar is standing on. The specific formula of the new value of the avatar
 * datacube is determined by extending subclasses.
 *
 * @author Harrison Huang
 */
public abstract class MathematicalCommands extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public MathematicalCommands(ElementInformationBundle elementInformationBundle,
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
    if (currTile.getBlock() instanceof DataCube tileCube && avatar
        .getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = calculateNewDisplayNum(avatarCube.getDisplayNum(),
          tileCube.getDisplayNum());
      avatarCube.setDisplayNum(newDisplayNum);
      System.out.println("Newly added NUMBER: " + newDisplayNum);

      sendDataCubeNumUpdate(avatarCube);
    } else {
      //if desired, handle error if the datacubes are not present and cannot complete operation
      //System.out.println("Cannot compute blocks!");
    }

    incrementProgramCounterByOne(avatar);
  }

  /**
   * The formula for calculating the new display number of the dataCube held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarCubeNum The current number of the cube held by the avatar
   * @param tileCubeNum   The current number of the cube on the tile
   * @return The new number to be set to the avatar cube
   */
  public abstract int calculateNewDisplayNum(int avatarCubeNum, int tileCubeNum);
}
