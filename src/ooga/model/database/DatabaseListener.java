package ooga.model.database;

/**
 * @author billyluqiu
 * Interface that gives listeners avaiable to program that need to be attached.
 * Example usage is concreteDatabaseListener implementing the interface.
 * Listeners are needed to update game state for multiplayer especially with regards to code update
 * and making sure scores are synced between players and teams, and a valid winner is able to be declared.
 *
 * All listeners are attached at the start of a level, and detached from Firebase upon level completion by
 * both teams in a multiplayer setting.
 *
 *
 */
public interface DatabaseListener {

  /**
   * Method that will attach a listener that will call separate call back function when the code area
   * is changed on the database for the current game.
   */
  void codeAreaChanged();
  /**
   * Method that will attach a listener that will call separate call back function when the level ends
   * for the current team because one person has finished the level
   */
  void checkLevelEndedForCurrentTeam();
  /**
   * Method that will attach a listener that will call separate call back function when the level ends
   * because both teams have finished the game succesfully (or the time has run out)
   */
  void checkLevelEndedForBothTeams();
  /**
   * Method that will attach a listener that will call separate call back function when the level starts
   * because there are two poeple on each team for the current game.
   */
  void checkLevelStarted();

}
