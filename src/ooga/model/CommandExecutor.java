package ooga.model;

import java.util.*;
import java.util.Map.Entry;
import javafx.scene.web.HTMLEditorSkin.Command;
import ooga.controller.BackEndExternalAPI;
import ooga.model.animation.AnimationPane;
import ooga.model.grid.GameGrid;
import ooga.model.grid.gridData.BoardState;
import ooga.model.player.Avatar;
import ooga.model.player.Element;
import ooga.view.level.Board;
import ooga.view.level.codearea.CommandBlock;

/**
 * 
 */
public class CommandExecutor {

    private List<CommandBlock> commandBlocks;
    private Map<Integer, CommandBlock> mapOfCommandBlocks;
    private int programCounter;
    private BackEndExternalAPI modelController;
    private GameGrid gameGrid;
    private BoardState initialState;
    private AnimationPane animationPane;
    /**
     * Default constructor
     */
    public CommandExecutor(List<CommandBlock> commandBlocks, BackEndExternalAPI modelController, BoardState initialState) {
        this.initialState = initialState;
        programCounter = 1;
        gameGrid = new GameGrid(modelController);
        animationPane = new AnimationPane(modelController);
        this.modelController = modelController;
        mapOfCommandBlocks = new HashMap<>();
        System.out.println("Command block received from frontend");
        System.out.println("Initial Avatar Positions: " + initialState.getAllAvatarLocations().entrySet());

        // add all avatars to animation
        for(Map.Entry<String, List<Integer>> entry : initialState.getAllAvatarLocations().entrySet()){
            animationPane.createAvatar(Integer.parseInt(entry.getKey()),new Avatar(Integer.parseInt(entry.getKey())));
        }

    //    System.out.println("Initial State of the Board: " + initialState.getAllBlockData().get("1").getLocation());

        // build a map of all commands to be executed
        buildCommandMap(commandBlocks);
    }

    private void buildCommandMap(List<CommandBlock> commandBlocks) {
        for(CommandBlock commandBlock : commandBlocks){
            mapOfCommandBlocks.put(commandBlock.getIndex(), commandBlock);
        }
        this.commandBlocks = commandBlocks;
    }

    public void runNextCommand() {
        boolean ended = true;
        System.out.println();
        for (Map.Entry<Integer, Element> entry : animationPane.getAllElementInformation().entrySet()){
            Avatar dummy = (Avatar) entry.getValue();
            
            // +1 is needed because program counters are 1 indexed
            if (dummy.getProgramCounter() < mapOfCommandBlocks.size() + 1) {

                ended = false;
                CommandBlock currentCommand = mapOfCommandBlocks.get(dummy.getProgramCounter());
                System.out.println("Command currently running: " + currentCommand.getType() + " with parameter " + currentCommand.getParameters());
                if(currentCommand.getType().equals("step")){

                    System.out.printf("Executing step for avatar ID %d with program counter %d \n", dummy.getId(), dummy.getProgramCounter());

                    // update program counter
                    dummy.setProgramCounter(dummy.getProgramCounter() + 1);
                }

                if(currentCommand.getType().equals("drop")){
                    System.out.printf("Executing drop for avatar ID %d with program counter %d \n", dummy.getId(), dummy.getProgramCounter());
                    // update program counter
                    dummy.setProgramCounter(dummy.getProgramCounter() + 1);
                }

                if(currentCommand.getType().equals("pickUp")){
                    System.out.printf("Executing pickUp for avatar ID %d with program counter %d \n", dummy.getId(), dummy.getProgramCounter());
                    // update program counter
                    dummy.setProgramCounter(dummy.getProgramCounter() + 1);
                }


            }
        }

        if(ended){

            modelController.declareEndOfAnimation();
        }

    }
}