/**
  * Parses json formatted file to create game instance 
  *
  * @author billyluqiu
  *
*/
public interface DataFileParser {

  /**
   * gets grid of game
   * 
   * @return grid of game
   */
  public Grid ParseGrid();

/**
* gets list of commands avaiable to the current user
* @ return list of commands
*/
  public List<Command> commandsAvailable();


}