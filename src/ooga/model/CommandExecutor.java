package ooga.model;

import java.util.*;
import java.util.Map.Entry;
import ooga.controller.BackEndExternalAPI;
import ooga.model.animation.AnimationPane;
import ooga.model.grid.GameGrid;
import ooga.model.grid.gridData.BoardState;
import ooga.model.player.Avatar;
import ooga.model.player.AvatarData;
import ooga.model.player.Element;
import ooga.model.player.ElementData;
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
    private int score;
    /**
     * Default constructor
     */
    public CommandExecutor(List<CommandBlock> commandBlocks, BackEndExternalAPI modelController, BoardState initialState,
        GameGrid gameGrid) {
        this.initialState = initialState;
        programCounter = 1;
        this.gameGrid = gameGrid;
//        System.out.println("Avatar IDs: " + gameGrid.getAvatarIds());
//        System.out.println("Avatars: " + gameGrid.getAvatarList());
        this.modelController = modelController;
        mapOfCommandBlocks = new HashMap<>();
        score = 0;
//        System.out.println("Command block received from frontend");
//        System.out.println("Initial Avatar Positions: " + initialState.getAllAvatarLocations().entrySet());
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
//        System.out.println();
        Map<Integer, Integer> lineUpdates = new HashMap<>();

        //Map<ID, Values>
        for (Avatar avatar : gameGrid.getAvatarList()){

            // +1 is needed because program counters are 1 indexed
            // TODO: refactor with Reflection and properties files
            if (avatar.getProgramCounter() < mapOfCommandBlocks.size() + 1) {
           //     modelController.setAvatarIDForUpdate(singleAvatar.getId());

                lineUpdates.put(avatar.getId(), avatar.getProgramCounter());
                score++;
                modelController.updateScore(score);
                ended = false;
                CommandBlock currentCommand = mapOfCommandBlocks.get(avatar.getProgramCounter());
//                System.out.printf("Running command #%d for avatar ID: %d\n", singleAvatar.getProgramCounter(), singleAvatar.getId());
                AvatarData newUpdate = new AvatarData();
//                System.out.println("Command currently running: " + currentCommand.getType() + " with parameter " + currentCommand.getParameters());
                if(currentCommand.getType().equals("step")){

//                    System.out.printf("Executing step for avatar ID %d with program counter %d \n", singleAvatar.getId(), singleAvatar.getProgramCounter());
//                    animationPane.moveAvatar(dummy, getDirection(currentCommand.getParameters().get("direction")));
                    int xPrev = avatar.getXCoord();
                    int yPrev = avatar.getYCoord();

               //     singleAvatar.step(getDirection(currentCommand.getParameters().get("direction")));
            //        gameGrid.step(dummy.getId(),getDirection(currentCommand.getParameters().get("direction")));
                    // update program counter

                    gameGrid.step(avatar.getId(),getDirection(currentCommand.getParameters().get("direction")));
//                    List<Integer> avatarCoordinates = gameGrid.getAvatarCoords(avatar.getId());
//                    avatar.setXCoord(avatarCoordinates.get(0));
//                    avatar.setYCoord(avatarCoordinates.get(1));

                    newUpdate.updatePositions(avatar.getId(), avatar.getXCoord(), avatar.getYCoord(), avatar.getProgramCounter());
           //         gameGrid.step(singleAvatar.getId(),getDirection(currentCommand.getParameters().get("direction")));

                    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
                    //TODO: delete after debug
                    modelController.updateAvatarPositions(avatar.getId(), avatar.getXCoord(), avatar.getYCoord());


                    //TODO: refactor using a better data structure
                 //   updates.put("step", newUpdate);

                }

                if(currentCommand.getType().equals("drop")){
    //                System.out.printf("Executing drop for avatar ID %d with program counter %d \n", singleAvatar.getId(), singleAvatar.getProgramCounter());
                    // update program counter
                    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
                }

                if(currentCommand.getType().equals("pickUp")){
//                    System.out.printf("Executing pickUp for avatar ID %d with program counter %d \n", singleAvatar.getId(), singleAvatar.getProgramCounter());
                    // update program counter
                    avatar.setProgramCounter(avatar.getProgramCounter() + 1);
                }
                if(currentCommand.getType().equals("jump")){
                    avatar.setProgramCounter(Integer.parseInt(currentCommand.getParameters().get("destination")));
                }


            }
        }


        modelController.setLineIndicators(lineUpdates);


        //TODO: refactor using a better data structure
     //   modelController.updateFrontEndElements(updates);

        if(ended){
            System.out.println("SCORE (CommandExecutor): " + score);
            modelController.declareEndOfAnimation();

            score = 0;
        }

    }


    // TODO: refactor with Reflection
    private Direction getDirection(String direction) {
        Direction dummy = Direction.SELF;
        if(direction.equals("up")){
            dummy = Direction.UP;
        }
        if(direction.equals("up-right")){
            dummy = Direction.UP_RIGHT;
        }
        if(direction.equals("right")){
            dummy = Direction.RIGHT;
        }
        if(direction.equals("down-right")){
            dummy = Direction.DOWN_RIGHT;
        }
        if(direction.equals("down")){
            dummy = Direction.DOWN;
        }
        if(direction.equals("down-left")){
            dummy = Direction.DOWN_LEFT;
        }
        if(direction.equals("left")){
            dummy = Direction.LEFT;
        }
        if(direction.equals("up-left")){
            dummy = Direction.UP_LEFT;
        }
        return dummy;
    }
}