package ooga.view.animation;

public interface AnimationAPI {

  /**
   * Pauses the animation
   */
  void pause();

  /**
   * plays or resumes the animation
   */
  void play();

  /**
   * resets the animation
   */
  void reset();

  /**
   * animates one iteration of a round
   */
  void step();

  /**
   * declares end of animation for the coding block
   */
  void declareEndOfRun();

  /**
   * stops animation
   */
  void stopAnimation();

}
