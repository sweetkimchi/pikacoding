package ooga.controller;

import java.util.List;
import java.util.Map;
import ooga.model.CommandExecutor;
import ooga.model.database.FirebaseService;
import ooga.model.parser.InitialConfigurationParser;
import ooga.view.level.codearea.CommandBlock;

/**
 *
 */
public class ModelController implements BackEndExternalAPI {

  private FrontEndExternalAPI viewController;
  private CommandExecutor commandExecutor;
  private InitialConfigurationParser initialConfigurationParser;
  private FirebaseService firebaseService;
  private int level;

  /**
   * Default constructor
   */
  public ModelController() {
    firebaseService = new FirebaseService();
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
   *
   * @param level integer indicating the level
   * @return BoardState object with level information
   */
  @Override
  public void initializeLevel(int level) {
    this.level = level;
    initialConfigurationParser = new InitialConfigurationParser(level, this.firebaseService);
    viewController.setBoard(initialConfigurationParser.getGameGridData(),
        initialConfigurationParser.getInitialState());
    viewController.setDescription(initialConfigurationParser.getDescription());
    viewController.setAvailableCommands(initialConfigurationParser.getAvailableCommands());
    viewController.setStartingApples(initialConfigurationParser.getGoalState().getNumOfCommands());
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
    initialConfigurationParser = new InitialConfigurationParser(this.level, this.firebaseService);

    commandExecutor = new CommandExecutor(commandBlocks, this,
        initialConfigurationParser.getInitialState(),
        initialConfigurationParser.getGameGrid(), initialConfigurationParser.getGoalState());
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
  public void declareEndOfAnimation() {
    viewController.declareEndOfAnimation();
  }

  /**
   * updates the score and sends it to the frontend
   *
   * @param score
   */
  @Override
  public void setScore(int score) {
    viewController.setScore(score);
  }

  /**
   * updates the line numbers for the avatars
   *
   * @param lineUpdates
   */
  @Override
  public void setLineIndicators(Map<Integer, Integer> lineUpdates) {
    viewController.setLineIndicators(lineUpdates);
  }

  @Override
  public void winLevel() {
    viewController.winLevel();
  }

  @Override
  public void loseLevel() {
    viewController.loseLevel();
  }

  @Override
  public void updateProgram(List<CommandBlock> program) {

    int matchID = 1;

    // TODO: notify database of program update
    System.out.print("Program received (ModelController): ");
    for(CommandBlock commandBlock : program){
      System.out.print(" " + commandBlock.getType());
    }
    System.out.println();

    firebaseService.saveMatchInformation(matchID, program);
  }
}