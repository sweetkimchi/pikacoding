public class FrontEndUseCases {

  Mock codeArea = mock(CodeArea.class);
  Mock viewController = mock(FrontEndExternalAPI.class);

  // The player drags and drops a `step` block from the CommandBank to the CodeArea
  public void addNewCommandBlock() {
    // When the user clicks and holds a command block from the command CommandBank
    CommandBlock heldCommand = new CommandBlock();
    // When the user releases the block in the program
    codeArea.addCommand(hoveredLineNumber, heldCommand);
  }

  // The player clicks the "play" button to start the execution of commands
  public void startCommandExecution() {
    // The player clicks the button, triggering an event handler in the View
    viewController.parseAndExecuteCommands(codeArea.getProgram());
  }

  // The player moves a command block in the CodeArea to a different line
  public void moveCommandBlock() {
    // Determine which block is being held by the player's cursor
    CommandBlock selectedBlock = getHeldBlock();
    int startIndex = selectedBlock.getIndex();
    // When the user releases the block at the new line number
    codeArea.shiftCommand(startIndex, newLineNumber, commandBlock);
  }

}