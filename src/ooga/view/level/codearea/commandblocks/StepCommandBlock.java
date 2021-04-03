package ooga.view.level.codearea.commandblocks;

import ooga.view.level.codearea.CommandBlock;

public class StepCommandBlock extends CommandBlock {

  public StepCommandBlock(int index) {
    super(index);
  }

  @Override
  public String getType() {
    return "step";
  }

  @Override
  protected void initializeParameters() {
    this.getParameters().add(0);
  }
}
