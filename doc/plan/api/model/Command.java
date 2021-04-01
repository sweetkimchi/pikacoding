/**
 * A command is an object that executes upon an Avatar. It can be a command for the avatar to act or
 * represent a conditional operation.
 * 
 * @author Harrison Huang
 * @author Ji Yun Hyo
 */
public interface Command {

  /**
   * Executes the command on an Avatar.
   * 
   * @param avatar The avatar upon which to execute the command
   */
  public void execute(CommandBlock command);
}