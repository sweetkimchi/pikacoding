package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

/**
 * Set DataCube Commands are a subclass of Basic Commands that set a value to the datacube held by
 * the Avatar based on the current value of the avatar datacube. The specific formula of the new
 * value of the avatar datacube is determined by extending subclasses.
 *
 * @author Harrison Huang
 */
public abstract class SetDataCubeCommands extends BasicCommands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public SetDataCubeCommands(ElementInformationBundle elementInformationBundle,
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
    Block block = avatar.getHeldItem();
    if (block != null) {
      int newDisplayNum = calculateNewDisplayNum(block.getDisplayNum());
      block.setDisplayNum(newDisplayNum);
      sendDataCubeNumUpdate(block);
    } else {
      //if desired, handle error if the avatar is not holding a datacube
      //System.out.println("Avatar is not holding a datacube!");
    }
    incrementProgramCounterByOne(avatar);
  }

  /**
   * The formula for calculating the new display number of the block held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarBlockNum The current number of the block held by the avatar
   * @return The new number to be set to the avatar block
   */
  public abstract int calculateNewDisplayNum(int avatarBlockNum);

}
