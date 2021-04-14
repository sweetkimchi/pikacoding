package ooga.model.commands;

import ooga.model.grid.GameGrid;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

public class Add extends BasicCommands {

  private final GameGrid gameGrid;

  public Add(GameGrid gameGrid) {
    this.gameGrid = gameGrid;
  }

  @Override
  public void execute(Avatar avatar) {
    Block avatarBlock = avatar.getHeldItem();
    Block gridBlock = gameGrid.getTile(avatar.getXCoord(), avatar.getYCoord()).getBlock();
    //TODO: avoid instanceof
    if (avatarBlock instanceof DataCube avatarCube && gridBlock instanceof DataCube gridCube) {
      avatarCube.setDisplayNum(avatarCube.getDisplayNum() + gridCube.getDisplayNum());
    } else {
      //TODO: handle error
      System.out.println("You cannot add here!");
    }
  }
}
