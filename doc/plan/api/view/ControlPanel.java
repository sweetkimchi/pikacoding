public interface ControlPanel {

  /**
   * Attaches an event handler that handles what happens when the play button is clicked.
   * @param eventHandler The play button event handler
   */
  public void setPlayEventHandler(EventHandler<ActionEvent> eventHandler);

  /**
   * Attaches an event handler that handles what happens when the stop button is clicked.
   * @param eventHandler The stop button event handler
   */
  public void setStopEventHandler(EventHandler<ActionEvent> eventHandler);

  /**
   * Attaches an event handler that handles what happens when the step button is clicked.
   * @param eventHandler The step button event handler
   */
  public void setStepEventHandler(EventHandler<ActionEvent> eventHandler);

  /**
   * Attaches a change listener that listens for when the slider value changes
   * @param changeListener The slider change listener
   */
  public void setSliderChangeListener(ChangeListener<? super T> changeListener);

}