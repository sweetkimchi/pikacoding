package ooga.model;

import java.util.*;
import ooga.controller.BackEndExternalAPI;
import ooga.model.animation.AnimationPane;
import ooga.model.commands.Commands;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.gridData.BoardState;
import ooga.model.grid.gridData.GoalState;
import ooga.model.player.Player;
import ooga.view.level.codearea.CommandBlock;

/**
 * 
 */
public class CommandExecutor {

    private List<Commands> commandBlocks;
    private Map<Integer, CommandBlock> mapOfCommandBlocks;
    private int programCounter;
    private BackEndExternalAPI modelController;
    private ElementInformationBundle elementInformationBundle;
    private BoardState initialState;
    private AnimationPane animationPane;
    private int score;
    private ClassLoader classLoader;
    private final String COMMAND_CLASSES_PACKAGE = Commands.class.getPackageName();
    private GoalState goalState;
    private List<Integer> endCommandLines;
    private Map<Integer, Integer> idToCommandLines;
    private Stack<Integer> stackOfIfCommands;
    /**
     * Default constructor
     */
    public CommandExecutor(List<CommandBlock> commandBlocks, BackEndExternalAPI modelController,
        BoardState initialState,
        ElementInformationBundle elementInformationBundle,
        GoalState goalState) {
        this.goalState = goalState;
        this.initialState = initialState;
        programCounter = 1;
        this.elementInformationBundle = elementInformationBundle;
        this.elementInformationBundle.setModelController(modelController);
        this.commandBlocks = new ArrayList<>();
        this.modelController = modelController;
        this.idToCommandLines = new TreeMap<>();
        endCommandLines = new ArrayList<>();
        stackOfIfCommands = new Stack<>();
        classLoader = new ClassLoader() {
        };
        mapOfCommandBlocks = new HashMap<>();
        score = 0;
        buildCommandMap(commandBlocks);
        this.elementInformationBundle.setEndCommandLines(endCommandLines);
        this.elementInformationBundle.setMapOfCommandLines(idToCommandLines);
    }

    private void buildCommandMap(List<CommandBlock> commandBlocks) {
        for(CommandBlock commandBlock : commandBlocks){
            mapOfCommandBlocks.put(commandBlock.getIndex(), commandBlock);
            Commands newCommand = null;
            try {
                Class r = classLoader.loadClass(COMMAND_CLASSES_PACKAGE+"."+ commandBlock.getType().replaceAll("\\s", "").substring(0,1).toUpperCase() + commandBlock.getType().replaceAll("\\s", "").substring(1));
                Object command = r.getDeclaredConstructor(ElementInformationBundle.class, Map.class).newInstance(elementInformationBundle, commandBlock.getParameters());
                newCommand = (Commands) command;
            }catch (Exception ignored){
                System.out.println("Failed");
            }
            this.commandBlocks.add(newCommand);
            System.out.println(this.commandBlocks);

            if(commandBlock.getType().equals("if")){
                stackOfIfCommands.add(commandBlock.getIndex());
            }

            if(commandBlock.getType().equals("end if")){
                idToCommandLines.put(stackOfIfCommands.pop(), commandBlock.getIndex());
            }
        }

        System.out.println("Pairs: " + idToCommandLines);





        System.out.println(this.endCommandLines);
        System.out.println("MAP: " + this.idToCommandLines);




    }

    public void runNextCommand() {

        boolean ended = true;
        Map<Integer, Integer> lineUpdates = new HashMap<>();
        System.out.println("GOAL STATE NUMBER: " + goalState.getNumOfCommands());

        for(Player avatar : elementInformationBundle.getAvatarList()){
            if (avatar.getProgramCounter() < commandBlocks.size() + 1){
                ended = false;
                lineUpdates.put(avatar.getId(), avatar.getProgramCounter());
                commandBlocks.get(avatar.getProgramCounter() - 1).execute(avatar.getId());
                score++;
                modelController.updateScore(goalState.getNumOfCommands() - score);
            }
            if(goalState.checkGameEnded(elementInformationBundle)){
                System.out.println("GAME HAS ENDED");
                System.out.println("SCORE (CommandExecutor): " + score);
                ended = true;
                modelController.winLevel();
            }
            if((goalState.getNumOfCommands() - score) < 0){
//                System.out.println("Game still going");
                System.out.println("SCORE (CommandExecutor): " + score);
                modelController.updateScore(0);
                modelController.declareEndOfAnimation();
                score = 0;
            }
        }

        modelController.setLineIndicators(lineUpdates);

        if(ended){
            System.out.println("SCORE (CommandExecutor): " + score);
            modelController.declareEndOfAnimation();
            score = 0;
        }

    }
}