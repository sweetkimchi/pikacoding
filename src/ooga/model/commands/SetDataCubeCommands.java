package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;

public abstract class SetDataCubeCommands extends BasicCommands {

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
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
    if (avatar.getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = calculateNewDisplayNum(avatarCube.getDisplayNum());
      avatarCube.setDisplayNum(newDisplayNum);
      sendDataCubeNumUpdate(avatarCube);
    } else {
      //if desired, handle error if the avatar is not holding a datacube
      //System.out.println("Avatar is not holding a datacube!");
    }
    incrementProgramCounterByOne(avatar);
  }

  /**
   * The formula for calculating the new display number of the dataCube held by the avatar. The
   * implementation and formula of the new display number is to be handled by subclasses.
   *
   * @param avatarCubeNum The current number of the cube held by the avatar
   * @return The new number to be set to the avatar cube
   */
  public abstract int calculateNewDisplayNum(int avatarCubeNum);

}
