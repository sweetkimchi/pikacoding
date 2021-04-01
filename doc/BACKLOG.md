# Use Cases for Team Three Final Project

* The player loads a new level from the level selector, which instantiates a new game screen with a new puzzle and grid.
* The player clicks/drags and drops a `step` block from the CommandBank to the CodeArea
* The player moves a command block in the CodeArea to a different line
* The player deletes a command block in the CodeArea
* The player modifies the conditional in an `if` block
* The player clicks the "play" button to start the execution of commands
* The player clicks the "pause" button to temporarily pause the execution of commands
* The player slides the animation speed slider from 1.0x to 1.5x, which changes the animation speed
* The player clicks the options button, which opens the options menu
* The player selects a new theme in the options menus, which changes the theme of the game
* After the execution of commands, the list of updated sprites is sent to the frontend to be displayed.
* The player executes a command to direct the human avatar to move down one space.
* The player executes a command to direct the human avatar to pick up the datacube in front of it.
* The player executes a command to direct the human avatar to pick up the datacube in front of it, but there is no datacube there. However, no error message is shown. Nothing happens.
* The player executes a command to direct the human avatar to drop the datacube in front of it.
* The player attempts to direct the human avatar to walk into a space already occupied by an obstacle.
* The player resets the coding area to its initial state by clicking the reset button attached to the coding area.
* The player correctly writes code that accomplishes the given task.
* The player completes the level and sees a score corresponding to how well they performed.
* Score is based off of the number of blocks used and the number of steps that it took to execute reach the goal
* Nunber of blocks/number of steps are compared to preset value in the json value to get a score
* The player commands two human avatars in a loop to pick up the datacube in front of it if it exists, and else move forward. Both avatars check this conditional independently and correctly, so each avatar can find the datacube even though they are placed at different distances away from the avatars.
* The player executes a command that tries to move the avatar to a hole but the avatar does not move to the hole
* The player clicks "reset" in the animation panel which reloads the level but does not affect the commands in the coding area
* The player executes a command that only moves the avatars that have access to an unoccupied location
* The player executes a command that tries to place more than one avatar at the same location and the avatars should have no problem standing at the same location
* The player is able to next the commands intuitively by dragging and dropping the commands inside other commands that allow nested components (e.g. if)
* The player is able to step through the animation by clicking "Step" button
* The player uses the "jump" command to re-direct the execution of the code to a new line
* The player executes a series of command without any loops and sees the commands be executed in consecutive order (e.g. increasing line numbers)
* The player executes a series of command to have the avatar drop the datacube into a hole
* The player's code has finished running, but it does not complete the task so a message will be displayed to tell them to try again
* After failing the task, the level resets itself without resetting the code
* The player chooses which direction for the `step` block it chooses (what direction is the player stepping)
* The score screen after completing a level allows the player to go back to a home/level screen
* Level screen has some preset levels (created from files) --> level 1 - level 9 for example
* Level screen has a button that allows user to upload a new level from a json
* The player sees the goal of the current level explained in the area above the coding area.
* The player sees which one of the lines each of the avatars is executing by some kind of graphic indicator next to the lines in the coding area.
* The available commands in the toolbox is determined by the json file (changes as levels become harder)
* The player drops `jump` command block into the coding area and sees that the player can now select a line number for the program to jump to
* The player can change the order of commands already in the coding area by choosing a command and dragging it to a different location
* The player uses the `ForEachLocation` to check each of the locations to see if any of the locations contains a datacube
* As more commands appear in the coding area, a scroll bar allows user to navigate the code they have written
* The player clicks "save" in the coding area to save the code for later
* The player clicks "copy" in the coding area to copy the code that is in the area
* The player can save their data to a central web server to access later
* The player moves a block and that movement is present on their teammates screen as well
* The player runs code and the result of that execution is present on their teammates screen as well
* Position of the starting blocks is able to be changed with data files
* Position of the human is able to be changed with data files
* Blocks available to the user is able to be changed with initial config files
* Incorrect initial config file will throw error
* Incorrect block placement will throw error