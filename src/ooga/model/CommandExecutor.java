package ooga.model;

import java.util.*;
import ooga.controller.BackEndExternalAPI;
import ooga.model.animation.AnimationPane;
import ooga.model.commands.Commands;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.gridData.BoardState;
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
    /**
     * Default constructor
     */
    public CommandExecutor(List<CommandBlock> commandBlocks, BackEndExternalAPI modelController, BoardState initialState,
        ElementInformationBundle elementInformationBundle) {
        this.initialState = initialState;
        programCounter = 1;
        this.elementInformationBundle = elementInformationBundle;
        this.elementInformationBundle.setModelController(modelController);
        this.commandBlocks = new ArrayList<>();
        this.modelController = modelController;
        classLoader = new ClassLoader() {
        };
        mapOfCommandBlocks = new HashMap<>();
        score = 0;
        buildCommandMap(commandBlocks);
    }

    private void buildCommandMap(List<CommandBlock> commandBlocks) {
        for(CommandBlock commandBlock : commandBlocks){
            mapOfCommandBlocks.put(commandBlock.getIndex(), commandBlock);
            Commands newCommand = null;
            try {
                Class r = classLoader.loadClass(COMMAND_CLASSES_PACKAGE+"."+ commandBlock.getType().substring(0,1).toUpperCase() + commandBlock.getType().substring(1));
                Object command = r.getDeclaredConstructor(ElementInformationBundle.class, Map.class).newInstance(elementInformationBundle, commandBlock.getParameters());
                newCommand = (Commands) command;
            }catch (Exception ignored){
                System.out.println("Failed");
            }
            this.commandBlocks.add(newCommand);
        }
    }

    public void runNextCommand() {

        boolean ended = true;
        Map<Integer, Integer> lineUpdates = new HashMap<>();

//
        for(Player avatar : elementInformationBundle.getAvatarList()){
            if (avatar.getProgramCounter() < commandBlocks.size() + 1){
                ended = false;
                lineUpdates.put(avatar.getId(), avatar.getProgramCounter());
                commandBlocks.get(avatar.getProgramCounter() - 1).execute(avatar.getId());
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