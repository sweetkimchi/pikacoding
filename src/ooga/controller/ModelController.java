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
    //TODO: delete after debugging. Initializing level for testing purposes

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
    //TODO: delete after debugging. Initializing level for testing purposes
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

  @Override
  public void updateAvatarPosition(int id, int xCoord, int yCoord) {
    viewController.updateAvatarPosition(id, xCoord, yCoord);
  }

  @Override
  public void updateBlockPosition(int id, int xCoord, int yCoord) {
    viewController.updateBlockPosition(id, xCoord, yCoord);
  }

  @Override
  public void updateBlock(int id, boolean b) {
    viewController.updateBlock(id, b);
    System.out.println("Updating block " + id + " because now the blockheld is " + b);
  }

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

  @Override
  public void winLevel(int executionScore, int bonusFromNumberOfCommands, int bonusFromTimeTaken) {
    int total = executionScore + bonusFromNumberOfCommands + bonusFromTimeTaken;
    if (teamID != ModelController.SINGLE_PLAYER) firebaseService.declareEndOfGame(this.matchID, this.teamID, total);
    viewController.winLevel(executionScore, bonusFromNumberOfCommands, bonusFromTimeTaken);
  }

  @Override
  public void loseLevel() {
    viewController.loseLevel();
  }

  @Override
  public void updateProgram(List<CommandBlock> program) {
    // TODO: notify database of program update
    System.out.println(program);
    if (this.teamID != SINGLE_PLAYER) {
      this.concreteDatabaseListener.setLastCommandBlockForCurrentComputer(program);
      firebaseService.saveMatchInformation(matchID, teamID, program);
    }
  }

  @Override
  public void checkTimeLeftOrNot() {
    commandExecutor.checkTimeLeftOrNot();

  }

  @Override
  public void timedOut() {
    viewController.timedOut();
  }

  @Override
  public void updateTime(int timeLeft) {
    viewController.updateTime(timeLeft);
  }

  @Override
  public void receivedProgramUpdate(List<CommandBlock> program) {
    System.out.println("recieved update" + program);
    if (program != null)  {
      viewController.receiveProgramUpdates(program);
    }
  }

  @Override
  public void setTeamNumber(int teamNum) {
    System.out.println("here" + teamNum);
    this.teamID = teamNum;
    if (teamNum == SINGLE_PLAYER) {
      PlayerInitialization playerInitialization = new PlayerInitialization(this.matchID, this.teamID);
      this.playerID = playerInitialization.getPlayerID();
    }
    else  {
      if (this.firebaseService != null) {
        this.firebaseService = new FirebaseService();
      }
      PlayerInitialization playerInitialization = new PlayerInitialization(this.matchID, this.teamID);
      this.playerID = playerInitialization.getPlayerID();
      concreteDatabaseListener = new ConcreteDatabaseListener(this, matchID, this.teamID);
      concreteDatabaseListener.checkLevelStarted();
      if (playerInitialization.getErrorOccurred())  {
        throw new ExceptionHandler("two players already present in team");
      }
    }
  }

  @Override
  public void startGameAfterBothTeamsPresent() {
    viewController.notifyBothTeamsPresent();
  }

  @Override
  public void setMatchId(int id) {
    this.matchID = id;
    System.out.println("set match id");
  }

  @Override
  public void notifyCurrentTeamEnded(int score) {
    viewController.notifyCurrentTeamFinished(score);
    System.out.println("level ended for current team");
  }

  @Override
  public void notifyBothTeamsEnded(int team1_score, int team2_score) {
    matchID++;
    viewController.notifyBothTeamsFinished(team1_score, team2_score);
  }


}