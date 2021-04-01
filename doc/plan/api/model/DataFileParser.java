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
* 
*
*/
  public List<Command> commandsAvailable();


}