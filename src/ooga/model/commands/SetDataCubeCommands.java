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

  @Override
  public void execute(int ID) {
    Avatar avatar = getAvatar(ID);
    if (avatar.getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = calculateNewDisplayNum(avatarCube.getDisplayNum());
      avatarCube.setDisplayNum(newDisplayNum);
      sendDataCubeNumUpdate(avatarCube);
    } else {
      //TODO: throw error to handler, change message to be specific for operation?
      System.out.println("Avatar is not holding a datacube!");
    }
    incrementProgramCounterByOne(avatar);
  }

  public abstract int calculateNewDisplayNum(int avatarCubeNum);

}
