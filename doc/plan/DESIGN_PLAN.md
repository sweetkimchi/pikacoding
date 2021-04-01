# Final Project Design Plan

### Team Three

### Names

- David Li (dl303)
- Harrison Huang (hlh38)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)
- Billy Luqiu (wyl6)

## Introduction
A multiplayer coding game in which teams compete against other teams to solve coding puzzles more quickly/efficiently.

Game Story: Should be flexible/able to be implemented by the game designer; tentatively, office employees doing "work"

Primary design choices include making the game extendable to the different levels and different challanges and puzzles within each level. We want the front and back end to be mostly closed by the end of this project, meaning that everything that changes will happen in the properties files. 

The property files will all be open in that we can create new property files. 

The high level structure will be a MVC controller with the controller acting as an intermediatery between the model and the view. The view will be responsible for displaying the game state using javaFX. The model will be responsible for parsing through commands sent to the back end, and for all server client communication. 

## Overview

The game will be launched through Main.java which instantiates ViewController and ModelController blocks. ViewController initializes the View and awaits for user to click "Run." Once "Run" is clicked, View will call a method in ViewController which in turn calls a method in ModelController to pass the data to the backend. ModelController will call CommandExecuter to launch the model. Once the commands are parsed by the Parser and Command packages, the resulting state updates will be sent to the frontend through the ModelController.

![](https://i.imgur.com/nXCRtnw.png)

## Design Details
* Model
    * Grid
        * Contains the field with all of the avatars and blocks, specific to each new level
        * Can have spaces containing different types of terrain, such as avatars, blocks, walls/holes/obstacles, etc.
    * Avatar
        * Extended by Human, a character that can be changed by the player
        * Consider having NPC
    * Block
        * Extended by DataCube, a block that can be picked up by a human and that the human can interact with
    * Data File parser
        * Handles parsing of JSON files
    * API Handler
        * handles external API calls to cloud based server to communicate user info and for multiplayer impelementation
    * parser for commands
      * Gets data structure from front end
      * runs commands on the avatars/blocks
    * Commands
      * One Command class for each possible command, performs an action on an avatar or block

* View
    - `Level`
      - `Board`
          - Represented as a grid of objects (humans, blocks, obstacles, empty, hole)
          - Receives updates from the model when code is run
      - `CodeBuildingArea`
          - Represented as a stack of command blocks
          - Contains a `CommandBlockBank` where the player can pull `CommandBlock`s from
      - `ControlPanel`
          - Play, stop, step, animation speed slider
          - Whenever the player starts running code, need to pass the commands to the model through the controllers
* Controller
    - ViewController for the frontend
    - ModelController for the backend
    - Double dependency
    - ViewController passes information (code) to the backend by calling methods in ModelController
    - ModelController passes information (updated game states) to the frontend by calling methods in ViewController

     - Will have a controller that interfaces with the model and the view.
    - This controller must obtain the state of the grid from the model, and then decide how to act on it to make it show on the view
        - the movement passed up will not require the backend to know the actual location or
          information about the avatar but rather just the change in location that will be applied
          to the avatar
        - the controller will then go through these movements and translate them to locations for
          the view to use, and in doing so, check the validity of the moves, throwing exceptions if
          the commands are invalid (i.e. off the grid)
    - Additionally, it would have to take a command from the view and give it to the model to use.
        - view will pass it back to the parser in the backend through the controller in a format the parser is able to recognize and parse accordingly


## Design Considerations

* How to parse code input in frontend and send it to the model to be executed?
  * Option 1: View returns a list of strings, each representing a line of code
    * Pros: Less responsibility on view, simpler for frontend, no dependency on backend commands
    * Cons: needs semi-hard coding for the backend to be able to interpret string output
  * Option 2: View makes and returns list of commands
    * Pros: Easier for backend with no need for parser, less overall code
    * Cons: Gives excessive responsibility to frontend to do backend work, adds dependency to backend commands

* Two controller classes (ViewController and ModelController) vs one big controller class
  * Option 1: One big controller class
    * Pros: no double dependency between the sub-controller classes
    * Cons: doesn't adhere to SRP
  * Option 2: Two controller classes
    * Pros: separation of responsibility, adheres to SRP
    * Cons: double-dependency between the two controller classes
  * Conclusion: We're going to initially go with two controller classes. The controller component should also be broken down so that each class in the controller component serves one role. Having a separate controller for the frontend and the backend seems to be a better design because of SRP.

## Example Games

1. A basic game which has only a few commands available to the player. The player can control just one avatar and instruct it to manipulate a datacube as desired in order to fulfill the goal of the puzzle.

2. A more complex game which has many more commands available to the player, allowing for relatively complex logic. The player can control multiple avatars to manipulate many datacubes using one piece of code, run on all avatars simultaneously.

3. A game that implements multiplayer functionality. Players can cooperate to work on the same code snippets, with each player possibly having a different set of commands available to them. Running the code will result in the views of each player updating simultaneously.

## Test Plan

The overall goal of the testing plan is to test for all of the different possible commands, and all
of the different possible kinds of bad input

