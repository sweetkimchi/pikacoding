/**
 * The main game area that contains avatars, blocks, etc.
 */
public interface Board {

  /**
   * Sets the view board to contain a new level. Instantiates all the elements of the grid, including the dimensions and initial locations of humans and objects.
   * @param boardState The initial state of the board
   */
  public void setBoard(BoardState boardState);

  /**
   * Updates and individual sprite (avatars, block)
   * @param id Id of the sprite to be updated
   * @param spriteData Representation of element of the game
   */
  public void updateSprite(int id, SpriteData spriteData);

}