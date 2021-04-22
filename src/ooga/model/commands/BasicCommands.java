package ooga.model.commands;

import java.util.*;
import ooga.model.Direction;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Tile;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;
import ooga.model.player.Element;

/**
 * @author Ji Yun Hyo
 */
public abstract class BasicCommands extends Commands {

  /**
   * Default constructor
   */
  public BasicCommands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  public Tile getCurrTile(int id) {
    Avatar avatar = getAvatar(id);
    return getElementInformationBundle().getTile(avatar.getXCoord(), avatar.getYCoord());
  }

  public Tile getNextTile(int id, Direction direction) {
    Avatar avatar = getAvatar(id);
    int nextX = avatar.getXCoord() + direction.getXDel();
    int nextY = avatar.getYCoord() + direction.getYDel();
    return getElementInformationBundle().getTile(nextX, nextY);
  }

  public void incrementProgramCounterByOne(Avatar avatar) {
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }

  public void sendAvatarPositionUpdate(Avatar avatar) {
    getElementInformationBundle().getModelController()
        .updateAvatarPosition(avatar.getId(), avatar.getXCoord(), avatar.getYCoord());
  }

  public void sendBlockPositionUpdate(Block block) {
    getElementInformationBundle().getModelController()
        .updateBlockPosition(block.getId(), block.getXCoord(), block.getYCoord());
  }

  public void sendBlockHeldUpdate(Block block) {
    getElementInformationBundle().getModelController().updateBlock(block.getId(), block.isHeld());
  }

  public void sendDataCubeNumUpdate(DataCube dataCube) {
    getElementInformationBundle().getModelController().setBlockNumber(dataCube.getId(),
        dataCube.getDisplayNum());
  }

  public void sendElementIsActiveUpdate(Element element) {
    // TODO: tell front end that the element is inactive (i.e. fell in a hole)
  }

}