/**
 * APIHandler to update game state in order to handle multiplayer functionality
 *
 * @author billyluqiu
 *
 */
public interface APIHandler {

  /**
   * updates the grid state to be in sync with the other grid
   * Changes based off of what the other user does
   *
   */
  public void syncGrid();

  /**
   * Sends data to the server to sync with the other grid
   * Takes a snapshot of the current grid and saves it as json and sends it to rest API server
   *
   */
  public void sendData();

}