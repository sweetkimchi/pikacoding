public class BackEndUseCases implements BackEndExternalAPI {

  Mock grid = mock(Grid.class);
  Mock modelController = mock(BackEndExternalAPI.class);
  Mock commandExecuter = mock(CommandExecutor.class);
  Mock parser = mock (Parser.class);

  public void parseAndExecuteCommands(Mock parameters) {
    // parse the commands and execute the command using the command executor
    commandExecuter.executeCommands(parser.parseCommand(parameters));

  }

  
  public List<Double> setPosition(double x, double y, int id){
    // sets the position of the object described by the id 
    modelController.setPosition(x,y,id);
  }
  
}