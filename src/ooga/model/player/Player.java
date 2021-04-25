package ooga.model.player;

/**
 * The Player is a general abstraction for characters in the game able to be manipulated by
 * commands. Each Player has an associated program counter in order to be able to execute the code
 * given by the player of the game. Each Player also has the ability to be able to pick up and drop
 * blocks, to be implemented in extending classes.
 *
 * @author Harrison Huang
 */
public abstract class Player implements Element {

  private static final int INITIAL_PC = 1;
  private int programCounter;

  /**
   * Default constructor of Player. Sets the program counter to be the initial program counter.
   */
  public Player() {
    programCounter = INITIAL_PC;
  }

  /**
   * Sets the program counter of the player to have a new value.
   *
   * @param programCounter The new program counter
   */
  public void setProgramCounter(int programCounter) {
    this.programCounter = programCounter;
  }

  /**
   * Getter for the value of program counter of the player.
   *
   * @return The current program counter value
   */
  public int getProgramCounter() {
    return programCounter;
  }

  /**
   * Directs the avatar to drop the block it is holding. Returns the block it dropped. The
   * implementation is to be determined by extending classes.
   *
   * @return The block that was dropped
   */
  public abstract Block drop();

  /**
   * Directs the avatar to pick up a block. The implementation is to be determined by extending
   * classes.
   *
   * @param block The block to pick up
   */
  public abstract void pickUp(Block block);
}