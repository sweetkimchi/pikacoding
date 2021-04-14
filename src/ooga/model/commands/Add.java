package ooga.model.commands;

import java.util.Map;
import javax.xml.crypto.Data;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

public class Add extends BasicCommands {

  public Add(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    super(elementInformationBundle,parameters);
  }

  @Override
  public void execute(int ID) {
    Avatar avatar = (Avatar) getElementInformationBundle().getAvatarById(ID);
    int currX = avatar.getXCoord();
    int currY = avatar.getYCoord();
    Tile currTile = getElementInformationBundle().getTile(currX,currY);
    if (currTile.getBlock() instanceof DataCube tileCube && avatar
        .getHeldItem() instanceof DataCube avatarCube) {
      int newDisplayNum = avatarCube.getDisplayNum() + tileCube.getDisplayNum();
      avatarCube.setDisplayNum(newDisplayNum);
    } else {
      //TODO: throw error to handler
      System.out.println("Cannot add blocks!");
    }

    avatar.setProgramCounter(avatar.getProgramCounter() + 1);

  }
}
