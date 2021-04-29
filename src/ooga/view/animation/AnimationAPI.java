package ooga.view.animation;

/**
 * A frontend internal API that gets executed when the user interacts with the animation buttons.
 * Methods in this class are called in LevelView so that LevelView can update the state of the animation
 * according to what AnimationController is telling it.
 * @author Ji Yun Hyo
 */
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
