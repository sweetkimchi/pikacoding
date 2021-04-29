package ooga.model;


/**
 * This class takes in the list of CommandBlock objects from the frontend, parses the commands,
 * and executes the commands. This class is the main class ModelController interacts with
 * in order to communicate with the backend.
 * @author Ji Yun Hyo
 */
public interface Executor {

  /**
   * Runs next commands passed to the backend
   */
  void runNextCommand();

  /**
   * Checks whether the game has timed out for not
   */
  void checkTimeLeftOrNot();

}
