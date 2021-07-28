ooga Team Three
====

This project implements a player for multiple related games.

**Names:**
David Li (dl303)
Harrison Huang (hlh38)
Ji Yun Hyo (jh160)
Billy Luqiu (wyl6)
Kathleen Chen (kc387)

# Demo Video
https://drive.google.com/file/d/14pG5U72Pi7gvV8VBdKh32MgdVoV0fsXR/view?usp=sharing

### Timeline

Start Date: 4/1/2021

Finish Date: 4/25/2021

Hours Spent:
* Billy: 25 hours/week -> ~100 hours total
* Jiyun: 150+ hours total
* David: 25 hours/week -> ~100 hours total
* Harrison: ~70 hours total
* Kathleen: 25 hours/week -> ~100 hours total


### Primary Roles
* Billy: Database/Data Files/Multiplayer Integration
* Jiyun: Animation/Controller/Commands Hierarchy/CommandExecutor/Writing Commands
* David: View/UI
* Harrison: Backend Logic/Game Elements/Writing Commands/Creative Level Design
* Kathleen: View/UI


### Resources Used
* https://randomprogrammingstuff.wordpress.com/2019/10/04/connect-to-google-firebase-using-admin-java-sdk-quick-start-tutorial/
* Firebase documentation https://firebase.google.com/
* Gson: https://github.com/google/gson


### Running the Program

Main class:

Run `Main.java`

#### How To Play

* Loading the start screen yields the title and three buttons to select start, color theme, and language.
* Clicking on start will prompt the user to enter a game ID, which should be shared between players who want to play together. This can be set to be any unique number if the user wants to play alone.
* The user can select whether to play in single player or multiplayer.
    * If the user selected to play multiplayer, they will require four people in total to play, two players on each of two teams.
    * Each player should join a team, and they will be able to start only when four players have joined the match.
* The level selection screen will then be displayed. If multiple players are playing together in multiplayer, then they should all select the same level.
* The level screen will be shown then. It is composed of a grid, code area, and animation buttons. The user will see a description of the puzzle to be completed in the top right and will have the available commands to them displayed in the code area below.
    * If the user is playing multiplayer, then only some of the commands will be available to them, with unavailable commands in red. Approximately half will be available to one teammate, and the other half will be available to the other teammate.
* The user can click on a command block in the side bar to add it to the code area. Each command block also contains a move button and a button for delete.
    * To move command blocks around, the user should click move on the block to be moved and the target block to which the block will move. The command blocks should shift around accordingly.
    * Some command blocks also contain dropdown boxes to select a parameter for the function, and this can be changed by the user. For example, the `step` command can be altered by the user to step in a specified direction.
* In the top left, there is a display for how many apples can be consumed by the avatars before the game stops. This corresponds to the maximum limit of the commands run before the solution is deemed too inefficient. For multiplayer, there is a timer near the top right of the grid, which gives a limit for how long the team can spend on the level.
* From left to right at the bottom, there are buttons for reset, play, pause, step, and a slider for animation speed, respectively.
    * These all work to control the function of the animation and command execution. The play button starts the animation, the pause button temporarily stops the animation, the step button either completes the current step in progress or moves the execution forward one step, and the reset button brings all avatars back to the initial state. Moving the slider to the right will speed up the animation, while moving the slider to the left will slow down the animation.
* In the middle of the screen is the main grid view. The grid contains different types of tiles (floor, wall, and hole) and  game elements, namely avatars and datacubes. Each avatar also has a uniquely assigned ID number, which is displayed next to the command block that it is currently running.
* The user will solve the level by adding and arranging command blocks in such a way so that the avatars complete the given challenge. This will bring up an end screen which displays the user's score and options to return or select another level.


Data files needed:
* Need a firebase access key that is placed as data/firebaseKey/key.json
* Need all image files listed in `images.json` for each of the levels
* Need all level files
    * commands.json
    * level#.json
    * startState.json
    * endState.json
    * grid.json
    * images.json

**Features implemented:**

Basic:
* load games
    * users are able load multiple different types of levels
    * each level can be designed flexibly by editing the following files
        * commands.json: specifies the available commands and parameters for the level
        * level#.json: contains the level information for both single player and multiplayer
            * commands available to each player
            * time limit
            * ideal number of commands
            * description
        * startState.json: contains the starting locations of the avatars and blocks, number on each block, and state of the block (e.g. held or not)
        * endState.json: contains the goal state and condition
        * grid.json: template of the level
            * width
            * height
            * grid represented by row and column
                * WALL
                * HOLE
                * FLOOR
        * images.json: contains the name of the image files to be used

Mild:
* Dynamic game rules
    * users can select single player or multiplayer
        * single player has no bonus points for solving the level fast but has no time limit
        * multiplayer has time limit but has bonus points for solving the level fast and with the least number of commands
    * Users are not able to select game rules, but game rules change based off level loaded such as blocks able to be used (and multiplayer places further restrictions on blocks that are able to be used)

Challenging:
* Networked players
    * users can join a match by inputting the same match IDs
    * two users make a team and two teams compete
    * each user has half the commands available to the team
    * through pair programming, the players have to use commands available to them to solve the puzzle
* Save game data in the web
    * game data (scores, commands on coding area, time, etc) and level data (templates) are stored on Firebase

Other features not part of the **Expectations** section
* Animation (Challenging)
    * the avatar ID is shown next to the current command it is executing
    * each avatar has an ID on its stomach
    * animation speed can be dynamically adjusted by dragging the slider
    * animating the avatar while it is moving to a new location
    * animating pickUp, throw, throwOver
    * implementing a mini animation to the next grid location using `Step` button
        * if the animation was running (e.g. pressed `Play`), then steps the animation to the next turn and pauses
        * if the animation was paused, then steps the animation to the next turn and pauses
    * `Pause` button stops the avatar animation but does not stop the timer
    * `Reset` button resets the avatars and reloads the level
        * resets the board regardless of the state of the animation
    * `Play` button plays the animation
        * if the round is finished, then resets and plays
        * if the game was paused, then resumes
        * if the animation was in the middle of `Step`, plays out the entire animation
* Commands (Basic & Mild)
    * Running commands on individual avatars as opposed to having a single or global avatar
        * Each avatar has its own program counter which allows different commands to be run simultaneously, especially in using conditional statements and jump commands
    * `CommandInterface` serves as the interface for all command classes
    * `Basic Commands` abstract class
        * Examples: `Step`, `Drop`, `Throw`, `PickUp`
            * Basic action commands that alter the location of game elements
    * `ConditionalCommands` abstract class
        * Examples: `If`
        * `If` can check the conditions of 9 adjacent blocks
            * comparator: `equals`, `not equals`
            * condition: `nothing`, `datacube`, `avatar`,`wall`,`hole`
    * `AICommands` abstract class
        * Examples: `Nearest`
            * moves the avatar towards the nearest block using Manhattan distance as the heuristic
    * `ControlFlowCommands`
        * Examples: `Jump`
            * takes in line number as a parameter and sets the program counter of the avatar to the line number
    * MathematicalCommands
        * Examples: `Add`, `Subtract`
* Error Handling (Basic & Mild)
    * handles error for wrong input of player ID by the user
    * handles error for parsing JSON files
    * errors are handled by `ExceptionHandler` class
        * surround a method with try/catch
        * `new throw ExceptionHandler("Error Message")` sends the error message key to the frontend to be displayed
* Goal state checking (Mild)
    * once the goal state is achieved, player has two options
        * continue to next level
        * play the round again to improve the score
* Single player (Basic)
    * unlimited time is given to the player
    * user can select levels
    * user can quit the level and select a new level to play
* Scoring System (Basic & Mild)
    * keeps the highest score of the user for that level
    * number of apples (e.g. lives) left for the avatar is displayed
        * everytime a command is executed by an avatar, the number of apples decreases by 1
        * if there are no apples left, player loses the level
    * takes into account the ideal number of lines and ideal number of commands (gives bonus at the end)
    * When avatar runs out of apples, player loses the game but can try again if there is time left
* Time limit per level (Mild & Challenging)
    * time limit is applied for multiplayer
        * timer is displayed in real time
        * when timer reaches 0, the game ends
        * win/lose screen is shown
    * time limit is not applied for single player
        * no time limit bonus
    * animation of time limit is separate from the animation of the avatar (challenging)
        * timer keeps running even when the avatar animation is paused
* Objective for each level (Basic)
    * objective for each level is displayed above the coding area
* Language Selection (Basic)
    * language selection is supported if we were to add more languages using properties files
* Coding Area (Mild & Challenging)
    * available commands for each level set using JSON file
    * users can add commands to the coding area by clicking on the commands from the available commands list
    * users can delete commands on the coding area by clicking 'X'
    * users can click `Move` next to the command blocks and intuitively switch command block locations
    * line numbers are displayed next to the commands
    * avatars are shown next to the command blocks as they are run and can differ between each other in the case of conditionals
    * code area dynamically changes to avoid syntax errors (e.g., moving an end if to be before the corresponding if, removing lines so as to invalidate a jump command's former placement)
* Splash Screen (Basic)
    * shown when the program is launched
    * can select preferences for color scheme
        *  blue
        *  dark
        *  default
        *  white
    *  select language preference
*  End Screen (Baisc)
    *  displays end screen once both teams have finished
*  Game Mode Selector Screen (Basic)
    *  select mode
        *  single player
        *  multiplayer
*  Level Selector Screen (Basic)
    *  launch a level by clicking on the level
*  Game Logic (Basic)
    *  avatars cannot move to WALL and HOLE
    *  avatars can move to a FLOOR
    *  datacubes can be placed on a FLOOR
    *  avatars can move in 8 directions (e.g. 4 diagonal locations and 4 adjacent locations)
    *  avatars can act on the datacube
        *  pick up, drop, throw, throw over, etc
        *  add, subtract, multiply, increment, set zero, etc





### Notes/Assumptions

Assumptions or Simplifications:
* Instead of displaying three different scores (execution score and two bonus scores), the total score will be displayed
* The backend and the frontend both know about the avatars but the frontend does not know specifically by what logic the avatars are moved.
* For multiplayer, players will have to enter the same `matchID` and select the same level in order to join the same game.
* For game, we wanted the levels to be challenging by not allowing the players to use sort of `Tell` command to move a specific avatar. They would need to use `If` command instead to move a specific set of avatars. However, if we were to implement the `Tell` command, it would be easy based on our current design since `CommandExecutor` would have to just check if the avatar's ID matches the parameter sent in through `Tell`
* Users would want to try the same level again even if they have solved it in order to get a higher score on the level
* Doing math operations on the blocks updates the number on the block the avatar is holding instead of on the block that is on the ground
* Single player has a time bound by a Java int (2^31 seconds = 68 years), which is essentially unlimited time

Design Decisions:
* Animation: We were debating whether to place `animation` package in the frontend or in the backend. The decision was made to put the `animation` package in the frontend because we wanted the animation class to be able to support any models and not be pertained to one specific model. More decision was made to separate the `AnimationAPI` from `Animation`. `Animation` stores the data and `AnimationAPI` is the controller class that other classes depend on to start the animation.

Interesting data files: Levels 9 and 10 are particularly interesting and challenging, as they each require loops with if statements within them in order to solve. Level 9 tasks you to be able to check whether you should use `throw` or `throwOver`, while Level 10 requires creativity in checking tile locations to come up with a solution.

Known Bugs: If the matchId already exists in Google Firebase, the player will not be able to enter the game (although this is a bug, we do error handling by informing the user that the match is full)

Extra credit:
* Multiplayer took a significant amount of time to run and debug, especially as we were trying to do "google docs" within our game
* Having our game programmatically avoid syntax errors was also challenging, as there were certain situations we always had to check for and avoid
* Animation was very challenging since `Play` and `Step` were essentially both animations played out (and paused) at different times. It was also challenging to display the timer in real time because doing so required animation.
