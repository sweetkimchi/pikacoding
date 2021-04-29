package ooga.view.level.codearea;

/**
 * Observer interface for a class that listens for updates in the program stack
 *
 * @author David Li
 */
public interface ProgramListener {

  /**
   * Called when the program stack gets updated
   */
  void onProgramUpdate();

}
