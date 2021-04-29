package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.CommandExecutor;
import ooga.model.Executor;
import ooga.model.database.FirebaseService;
import ooga.model.database.PlayerInitialization;
import ooga.model.database.parser.ConcreteDatabaseListener;
import ooga.model.database.parser.InitialConfigurationParser;
import ooga.model.exceptions.ExceptionHandler;
import ooga.view.level.codearea.CommandBlock;
import com.google.common.base.Stopwatch;

/**
 * @author Ji Yun Hyo
 * @author billyluqiu
 */
public class ModelController implements BackEndExternalAPI {

  public static final int SINGLE_PLAYER = 0;
  private FrontEndExternalAPI viewController;
  private Executor commandExecutor;
  private InitialConfigurationParser initialConfigurationParser;
  private FirebaseService firebaseService;
  private ConcreteDatabaseListener concreteDatabaseListener;
  private int level;
  private int matchID;
  private int teamID;
  private int playerID;
  private Stopwatch stopwatch;

  /**
   * Default constructor
   */
  public ModelController() {
    matchID = 4444;
    //firebaseService = new FirebaseService();
    matchID = 4444;
  }

  /**
   * sets the view controller to set up the line of communication from/to the backend
   *
   * @param viewController FrontEndExternalAPI
   */
  @Override
  public void setViewController(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }

  /**
   * initializes the level
   * @param level integer indicating the level
   */
  @Override
  public void initializeLevel(int level) {
    this.level = level;
    initialConfigurationParser = new InitialConfigurationParser(level, this.firebaseService, this.playerID);

    stopwatch = Stopwatch.createStarted();
    viewController.setBoard(initialConfigurationParser.getGameGridData(),
        initialConfigurationParser.getInitialState());
    viewController.setDescription(initialConfigurationParser.getDescription());
    viewController.setAvailableCommands(initialConfigurationParser.getAvailableCommands());
    viewController.setStartingApples(initialConfigurationParser.getGoalState().getNumOfCommands());

    commandExecutor = new CommandExecutor(new ArrayList<>(), this,
        initialConfigurationParser.getInitialState(),
        initialConfigurationParser.getGameGrid(), initialConfigurationParser.getGoalState(),stopwatch);


    if (this.teamID != SINGLE_PLAYER) {
      concreteDatabaseListener.codeAreaChanged();
      concreteDatabaseListener.checkLevelEndedForCurrentTeam();
      concreteDatabaseListener.checkLevelEndedForBothTeams();
      viewController.setAvailableCommandsOtherPlayer(initialConfigurationParser.getAvailableCommandsOtherPlayer());
    }
  }

  /**
   * Passes in the commands to be parsed
   *
   * @param commandBlocks List of individual command blocks derived from the blocks in the
   *                      CodeBuilderArea
   */
  @Override
  public void parseCommands(List<CommandBlock> commandBlocks) {
    initialConfigurationParser = new InitialConfigurationParser(this.level, this.firebaseService, this.playerID);

    commandExecutor = new CommandExecutor(commandBlocks, this,
        initialConfigurationParser.getInitialState(),
        initialConfigurationParser.getGameGrid(), initialConfigurationParser.getGoalState(),stopwatch);
  }

  /**
   * Runs the next command in the command queue
   */
  @Override
  public void runNextCommand() {
    commandExecutor.runNextCommand();
  }

  /**
   * Updates a specific avatar's position to new a new location on the grid
   *
   * @param id ID of the avatar
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  @Override
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    viewController.updateAvatarPosition(id, xCoord, yCoord);
  }

  /**
   * Updates a specific block's position to new a new location on the grid
   *
   * @param id ID of the block
   * @param xCoord new x coordinate
   * @param yCoord new y coordinate
   */
  @Override
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    viewController.updateBlockPosition(id, xCoord, yCoord);
  }

  /**
   * Update the status of the block
   * True = block is being held by an avatar
   * False = block is not being held by an avatar
   * @param id ID of the block
   * @param b boolean indicating whether the block is being held or not
   */
  @Override
  public void updateBlock(int id, boolean b) {
    viewController.updateBlock(id, b);
  }

  /**
   * Sets the number on the block to a new number
   * @param id ID of the block
   * @param newDisplayNum new number to be displayed on the block
   */
  @Override
  public void setBlockNumber(int id, int newDisplayNum) {
    viewController.setBlockNumber(id, newDisplayNum);
  }

  /**
   * All commands have reached the end and no more to be executed
   */
  @Override
  public void declareEndOfRun() {
    viewController.declareEndOfRun();
  }

  /**
   * updates the score and sends it to the frontend
   *
   * @param score score of the player
   */
  @Override
  public void setScore(int score) {
    viewController.setScore(score);
  }

  /**
   * updates the line numbers for the avatars
   *
   * @param lineUpdates line updates
   */
  @Override
  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    viewController.setLineIndicators(lineUpdates);
  }

  /**
   * Wins the level for the player. This method is the method that is called when the
   * goal state is met. It returns 3 sets of scores calculated according to the
   * game criteria: achieve less lines of commands, less number of execution of those commands,
   * and less time taken to win the level
   * @param executionScore score corresponding to how many lines were run
   * @param bonusFromNumberOfCommands score corresponding to how many number of commands were used
   * @param bonusFromTimeTaken score corresponding to how much time was kaen
   */
  @Override
  public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    int total = executionScore + bonusFromNumberOfCommands + bonusFromTimeTaken;
    if (teamID != ModelController.SINGLE_PLAYER) firebaseService.declareEndOfGame(this.matchID, this.teamID, total);
    viewController.winLevel(executionScore, bonusFromNumberOfCommands, bonusFromTimeTaken);
  }

  /**
   * Informs the frontend the player has lost the game. This method gets called whenever
   * the model determines that the game has been lost.
   */
  @Override
  public void loseLevel() {
    viewController.loseLevel();
  }

  /**
   * Updates the program (model) in the backend by providing it with a new set of
   * CommandBlock objects to parse and execute
   * @param program list of CommandBlock ojbects containing information about each command block
   */
  @Override
  public void updateProgram(List<CommandBlock> program) {
    if (this.teamID != SINGLE_PLAYER) {
      this.concreteDatabaseListener.setLastCommandBlockForCurrentComputer(program);
      firebaseService.saveMatchInformation(matchID, teamID, program);
    }
  }

  /**
   * Checks whether there is time left or not in the game. This method is called by the frontend
   * during the animation to see if they should proceed
   */
  @Override
  public void checkTimeLeftOrNot() {
    commandExecutor.checkTimeLeftOrNot();

  }

  /**
   * This method is called when the game has timed out.
   */
  @Override
  public void timedOut() {
    viewController.timedOut();
  }

  /**
   * Updates the time left. This method is part of the animation for show how much time
   * the player has left on the GUI
   * @param timeLeft seconds representing the amount of time the player has left
   */
  @Override
  public void updateTime(int timeLeft) {
    viewController.updateTime(timeLeft);
  }

  /**
   * Relays update program information
   * @param program list of commnd blocks
   */
  @Override
  public void receivedProgramUpdate(List<CommandBlock> program) {
    if (program != null)  {
      viewController.receiveProgramUpdates(program);
    }
  }

  /**
   * {@inheritDoc}
   * implementation also sets the player number and creates listeners as they are necessary items
   * when a team is selected.
   * @param teamNum team Num of the current team player has selected (MUST be 1 or 2)
   */
  @Override
  public void setTeamNumber(int teamNum) {
    //logic to see if it's single player or not
    this.teamID = teamNum;
    if (teamNum == SINGLE_PLAYER) {
      PlayerInitialization playerInitialization = new PlayerInitialization(this.matchID, this.teamID);
      this.playerID = playerInitialization.getPlayerID();
    }
    else  {
      this.firebaseService = new FirebaseService();
      PlayerInitialization playerInitialization = new PlayerInitialization(this.matchID, this.teamID);
      this.playerID = playerInitialization.getPlayerID();
      concreteDatabaseListener = new ConcreteDatabaseListener(this, matchID, this.teamID);
      concreteDatabaseListener.checkLevelStarted();
      if (playerInitialization.getErrorOccurred())  {
        throw new ExceptionHandler("two players already present in team");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startGameAfterBothTeamsPresent() {
    viewController.notifyBothTeamsPresent();
  }

  /**
   * {@inheritDoc}
   * @param id id of match
   */
  @Override
  public void setMatchId(int id) {
    this.matchID = id;
  }

  /**
   * {@inheritDoc}
   * @param score of the winning player
   */
  @Override
  public void notifyCurrentTeamEnded(int score) {
    viewController.notifyCurrentTeamFinished(score);
  }

  /**
   * {@inheritDoc}
   * @param team1_score score of the current player's team
   * @param team2_score score of the opposing team
   */
  @Override
  public void notifyBothTeamsEnded(int team1_score, int team2_score) {
    matchID++;
    viewController.notifyBothTeamsFinished(team1_score, team2_score);
  }


}