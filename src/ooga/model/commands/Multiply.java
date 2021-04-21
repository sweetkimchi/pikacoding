package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.DataCube;

public class Multiply extends BasicCommands{

  /**
   * Default constructor
   *
   * @param elementInformationBundle
   * @param parameters
   */
  public Multiply(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  @Override
  public void execute(int ID) {
    //TODO: remove duplication between add/subtract?
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    Tile currTile = getElementInformationBundle().getTile(currX,currY);
    if (currTile.getBlock() instanceof DataCube tileCube && avatar
        .getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = avatarCube.getDisplayNum() * tileCube.getDisplayNum();
      avatarCube.setDisplayNum(newDisplayNum);
      getElementInformationBundle().getModelController().setBoardNumber(avatarCube.getId(),newDisplayNum);
    } else {
      //TODO: throw error to handler
      System.out.println("Cannot subtract blocks!");
    }

    incrementProgramCounterByOne(avatar);

    //TODO: send updates to ElementInformationBundle

  }

}
