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
 * BasicCommands is a group of commands that perform elementary actions on an Avatar. It contains
 * helper methods in order to obtain and send necessary data easier.
 *
 * @author Ji Yun Hyo
 * @author Harrison Huang
 */
public abstract class BasicCommands extends Commands {

  /**
   * Base constructor of a command. Takes in an ElementInformationBundle and parameters custom to
   * the type of command.
   *
   * @param elementInformationBundle The ElementInformationBundle of the game
   * @param parameters               A Map of parameters to the command
   */
  public BasicCommands(ElementInformationBundle elementInformationBundle,
      Map<String, String> parameters) {
    super(elementInformationBundle, parameters);
  }

  /**
   * Getter for the tile occupied by the Avatar, which is queried by its ID.
   *
   * @param id The ID of the Avatar
   * @return The tile object that the Avatar is standing on
   */
  protected Tile getCurrTile(int id) {
    Avatar avatar = getAvatar(id);
    return getElementInformationBundle().getTile(avatar.getXCoord(), avatar.getYCoord());
  }

  /**
   * Getter for the tile occupied by the Avatar, which is queried by its ID, with the additional
   * parameter of a direction offset relative to that tile.
   *
   * @param id        The ID of the Avatar
   * @param direction The directional offset of the desired tile
   * @return The desired tile object
   */
  protected Tile getNextTile(int id, Direction direction) {
    Avatar avatar = getAvatar(id);
    int nextX = avatar.getXCoord() + direction.getXDel();
    int nextY = avatar.getYCoord() + direction.getYDel();
    return getElementInformationBundle().getTile(nextX, nextY);
  }

  /**
   * Increments the program counter of the avatar by one.
   *
   * @param avatar The avatar to be incremented
   */
  protected void incrementProgramCounterByOne(Avatar avatar) {
    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
  }

  /**
   * Sends a positional update of the input avatar to the model controller.
   *
   * @param avatar The avatar to be updated by position
   */
  protected void sendAvatarPositionUpdate(Avatar avatar) {
    getElementInformationBundle().getModelController()
        .updateAvatarPosition(avatar.getId(), avatar.getXCoord(), avatar.getYCoord());
  }

  /**
   * Sends a positional update of the input block to the model controller.
   *
   * @param block The block to be updated by position
   */
  protected void sendBlockPositionUpdate(Block block) {
    getElementInformationBundle().getModelController()
        .updateBlockPosition(block.getId(), block.getXCoord(), block.getYCoord());
  }

  /**
   * Sends an update of the status of the block being held to the model controller.
   *
   * @param block The block to be updated if it is held
   */
  protected void sendBlockHeldUpdate(Block block) {
    getElementInformationBundle().getModelController().updateBlock(block.getId(), block.isHeld());
  }

  /**
   * Sends an update of the display number of the block to the model controller.
   *
   * @param block The block whose number is to be updated
   */
  protected void sendDataCubeNumUpdate(Block block) {
    getElementInformationBundle().getModelController().setBlockNumber(block.getId(),
        block.getDisplayNum());
  }

}