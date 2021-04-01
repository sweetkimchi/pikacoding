public interface CodeArea {

  /**
   * @return A list of the command blocks that the player has constructed
   */
  public List<CommandBlock> getProgram();

  /**
   * Inserts a command to the program at the specified index
   * @param index The line number of the inserted command
   * @param commandBlock The CommandBlock to be added
   */
  void addCommand(int index, CommandBlock commandBlock);

  /**
   * Shhifts a command to the program from one index to another
   * @param startIndex The original line number of the command
   * @param endIndex The new line number of the command
   * @param commandBlock The CommandBlock being shifted
   */
  void shiftCommand(int startIndex, int endIndex, CommandBlock commandBlock);

  /**
   * Deletes a command in the program at the specified index
   * @param index The line number of the command being deleted
   * @param commandBlock The CommandBlock to be deleted
   */
  void deleteCommand(int index, CommandBlock commandBlock);

}