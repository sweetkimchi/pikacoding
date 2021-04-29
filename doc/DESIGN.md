# DESIGN Document for ooga Team Three
## NAME(s)
David Li (dl303)
Harrison Huang (hlh38)
Ji Yun Hyo (jh160)
Billy Luqiu (wyl6)
Kathleen Chen (kc387)


## Role(s)


Billy: I worked primarily on the data files for this program as they relate to game design. I designed the game files and a system to store the game in the back end. I also set up a centralized repository in Firebase to communicate game data and game state between players, making sure that game data was being synchronized across players with minimal latency. I integrated these features into the main game engine as well.

Harrison: I mainly worked on the model and our fundamental backend functionality, which includes the representation of the grid and game elements as objects. Similarly, I handled a lot of the commands and its abstraction, which act upon such game elements to perform a specific set of actions. I also worked as a creative lead for our game, which involved designing game elements, commands, and level functionality around possible game variations that we wanted.

David: I focused on the front-end of the project as well as connecting it with the view and model controllers. A large part of the front-end that I worked on was the code area and making sure that it sends and receives updates to and from the database in the correct way. I did a lot of miscellaneous work in various other parts of the view, like win/lose screens and some of the board.

Kathleen: I also focused mainly on the front-end of the project, mainly the UI design. I also worked a little bit on connecting the view controller to the model controller. The main thing I worked on in the view was making the different screens that the user would interact with when choosing which type of game they would play as well as the ViewAvatar and the ViewBlock. I also did most of the ResourceBundles and css files for the view.

Ji Yun: I focused on the overall design of the project as a whole using the UML diagram (excluding the database design), the overall implementation of animation (frontend), design and implementation of `Commands` classes hierarchy (backend), and wiring up the frontend with the backend through the API calls in the `Controller` classes (excluding parsing data from Firebase). When others were struggling with the design, I also jumped in and refactored the modules to allow for easy addition of features. I used the UML diagram to generate the starting code from the UML diagram.

## Design Goals


### Overall:

The overall goal of the design was to achieve a modular design in conjunction with SOLID design principles in order to allow maximum flexibility/readability and ease of adding/modifying features. We achieved this by adhering to a set of seven design principles we learned throughout the course. Every time we write a new method/class/interface/abstract class/package/etc, we asked ourselves whether these 7 pillars of a good design were met.

#### 1. Modular Design
* The goal was to allow each module (package) to essentially be replaceable by any concrete implementations of the module. Therefore, replacing the module with a different implementation would not break the software. This was also done in conjunction with the dependency inversion principle to have all the classes that have external/internal APIs depend on interfaces for API calls instead of depending on concrete implementations of each interface/abstract class.

#### 2. Dependency Inversion Principle (SOLID)
* The goal was to have all modules/classes depend on abstractions and not on concrete classes. Therefore, almost all of our modules have a main interface that defines the API methods that other modules can call to interact with them. We have both high-level and low-level objects depend on the same abstraction.

#### 3. Open-closed Principle (SOLID)
* The goal was to allow the software to be open for extension but closed for modification. With data files, we can create new levels, change avatar images, modify/create new goal states, modify the description of the level, and change the color scheme.
* Moreover, for inheritance, we provide many types of interfaces/abstract classes so that the coder can implement new features by extending/implementing the abstract classes/interfaces respectively without having to modify the original code.

#### 4. Single Responsibility Principle (SOLID)
* When designing the overall software using the UML diagram, we made sure each class/package/API/interface serves one responsibility
* When a class was found to serve more than one responsibility, the class was refactored until each refactored component served only one responbility

#### 5. Liskov Subsitution Principle (SOLID)
* We adhered to Liskov Subsitution principle by always returning a higher abstraction (abstract class/interface) object instead of returning an object of a concrete class. This is evident all throughout the project

#### 6. Interface segregation principle (SOLID)
* Instead of every sub-class of a module (package) depend on one single interface, we have many interfaces/abstract classes for some modules to provide client-specific interfaces instead of just having one general-purpose interface.

#### 7. DRY and SHY code
* When writing the specific methods, we made sure that our code is DRY and SHY as much as possible.


### Backend:

#### Data Files
* The core of the games files is **data driven**. All game state was to be stored in the data files. This was so that new levels could be created with existing blocks with only new data file. Thus, our game is highly flexible in that new levels are able to be added extremely easily with existing building blocks in our game such as a different set of commands for each game, differing Pikachu placement, and differeing goal states. It is up to the game designer's imagination to come up with new and innovate levels!
* We wanted to make easier integration with database. Therefore, we stored all game files in JSON format. We stored game files in the database, and have a helper main class in the Java backend for any developers that want to add new game levels to the database. We also standardized code area block data storage as would need to sync that between players on the same team. Lastly, we decided on an easy API to call when games were ended and when teams had finished playing the levels, as we needed to be able to communicate ending statuses of each player on each team independently across computers.

#### Database
* Our main goal in database choice was simplicity, ease of use, and real time synchornization. None of our team members were familiar with database usage before this project so we learned as we went, and needed something that had extensive documentation so that it was easily implementable, and a low latency when storing data files as we would be communicating game state through the database. We also wanted an easy interface to see what listeners had been attached to the database.
* We wanted single player to work without a network connection, so only multiplayer game play requires database hookup.

#### Model and Commands
* One big part of our project's design was that we wanted to be able to support both single and multiple avatars in order to be able to support game variations that demanded either option. This also played into the fact that each avatar needed to keep track of its own program counter so that it could execute code independent of how many avatars actually exist and what state those avatars are at.
* A big design goal for commands was to make them as extensible as possible and easy to implement. By building a list of commands to be executed dynamically by using `ClassLoader` to make concrete instances of the respective `Commands` classes, we were to implement new commands easily. To implement a new command, all we would have to do is to implement the `execute` command for each new `Commands` class. We also made this decision over using `Reflection` because `Reflection` has lots of overhead which we thought would be bad for multiplayer latency. By having abstract subclasses such as `BasicCommands` and `MathematicalCommands`, we were able to effectively implement new commands that were similar to one another with little duplication. These commands can also take in various parameters as defined by data files, which played into the overall extensibility as well.
* While it wasn't a big focus of our project, the extensibility of game elements was something we kept in mind. As such, the fundamental game elements, `Avatar` and `DataCube`, are abstracted as `Player` and `Block`, respectively, which allows for the support of new game elements that have similar but different functionality.


### Frontend:

#### Resource files:
* We wanted to make the UI as flexible as possible so we put most of our parameters, strings, and other often hard coded elements of the frontend into different datafiles for each class that we made.
* This way it is easy to change the names of different buttons and the values of different parameters without having to go into the Java code and change these values.
* By having the image information for animation be stored in resource files, it is very easy to change the avatar that is being displayed on the screen.
* By using resource files, it is also much easier to change the language that is being used by the program just by changing the String corresponding to the specific keys in the resource file

#### Multiple Classes:
* As a frontend we decided to use multiple classes in order to better follow the single responsibility principle.
* It is very easy in the view to have a class that does many different things simply if you split your classes just by pane or area of the screen.
* By using many different classes, we were better able to make sure each class only had one responsibility and would do that particular task well.

#### GUI Element Factory
* We decided to have a factory that would create different GUI elements such as buttons and labels.
* This decision was made because many different classes of the front end required the creation of buttons or labels.
* This resulted in a lot of duplicate code to construct these JavaFX elements.
* To alleviate this problem a GUIELementFactory class that implements a GUIElementInterface was created.
* In this factory class, centralized information on how to create certain GUI elements was stored.
* Since this class implemented an interface, it made it more protected when being added into the various different classes that needed this factory.
* With the prescense of this factory, the duplicate code to create these buttons and elements was taken care of and centralized in the factory class.


## High-Level Design

### Overall:

![](https://i.imgur.com/WBUZlkT.png)

* MVC framework implemented with each component represented in the UML diagram. Each of the MVC components interacts with each other through the API calls. We wanted to make sure that any of the modules could be replaced by a different concrete implementation of the API interfaces.
* When `Main.java` is run, `Controller.java` sets up two controller classes, `ViewController` and `ModelController`, each of which is an implementation of the `BackEndExternalAPI`/`FrontendExternalAPI` interfaces. `ViewController` calls `ScreenCreator` which launches the frontend GUI. The software waits for the player to select a game mode (e.g. single or multiplayer) and to select a level.
* When a level is selected, the software loads a level, `ScreenCreator` calls `initializeSingleLevel` method in `ViewController` to initialize. the level. If the game mode is single player, the level is loaded from a local data file. If the game mode is multiplayer, the level is mloaded from the Firebase database.
* Once the game is loaded and parsed, the information is passed to both the frontend and the backend. Frontend displays the level based on the grid that was built from the parser. Backend also creates a abstract representation of the grid in order to execute the logic of the model.
* Once the player hits the run button, the commands on the coding area gets sent to the backend through a series of API calls. The data arrives in `Executor` class which then turns the list of commands into a list of `Commands` objects. We then iterate through the list of commands and execute the command that matches the program counter of the avatar. Any updates are sent back to the frontend to be displayed through a series of API calls.
* We have made the design decision to send the specific updated information individually instead of sending everything back to the frontend each time a command is executed. This allows for better encapsulation since only the data that is useful (updated) is sent to the frontend.
* The backend has no idea how the frontend handles the information that is being sent.
* The `animation` module receives all the informati fronm the backend and prepares the animation. `AnimationController` then tells the view component what to be displayed.

### Backend:

* We store 6 data files per game level. We have a top level levelX.json which stores basic information about the game such as what blocks are avaiable to each player, the number of avatars, the ideal number of commands, time limit, and description. We have a start and goal state where we put the locations of the avatars and blocks on start of the program, and when they will need to be placed in order for the user to succesfully complete the level. We also have a grid class that gives the grid structure, and the commands available as well as their parameters for the specific game, meaning that the commands can have different attributes for different levels.
* All of our game data is stored in Firebase; we choose firebase due to the design considerations listed above. All of our game data therefore is being stored JSON-formatted in firebase.
* We parse game data differently depending on single/multi player. The game parsers have slightly different behavior as accesing raw data from Firebase is not possible; Firebase compresses JSONs in transit so we can't assume the invariant of what we store on firebase is the same data we will get back. Thus, we had to write a few if/else statements in the Parser to account for this. The parser reads in JSON formatted data, either from the data files directly, or from the (compressed) firebase data files, and turns them into gridData classes depending on how they're used. These data objects then get passed around to the front end as needed. We used **Dependency Injection** design pattern by using the parser class to populate all data objects and pass them to the classes that need them in the front end and back end accordingly. Thus, we have a centralized class to create all the data objects at the start of a level.
* We used an inheritance hierachy for boardStates, as goal state and initial state share the same parameters but each have different instance variables due to subtle differences, an ideal usage of **inheritance** and **polymorphism** as the front end is able to display state through superclass methods, representing **Liskov's subsitution principle** as well.
* We used a centralized class for database calls (FirebaseService) that was able to be called in the model Controller. Thus, model controller was able to make database calls through backend internal API calls. We format the JSON for format in the FirebaseService class, maintaining **single responsibility principles** as that class is able to communicate java classes to the database directly. We also follow **open closed princples** in that we can add new code for additional database calls (such as a live chat feature), but the features are closed for modification in that we have helper methods that package data and a amin method to push data to the database.
* We implemented database listeners using an **Observer** design pattern. The observer in this case also follows **interface segregation** principles in that we have segragated out all listeners for the multiplayer game so we have a centaralized list of what listeners are going at the same time, reducing unwanted behavior due to rouge interface calls. We implement listeners by having each listener call a target function in model controller, which can then communicate with other model classes and view classes accordingly.
* Multiplayer database communciation includes deciding when a game is able to start, when a game has ended for both teams, and updating the code area for both teams *Google Docs* style. We implemented all of this by using listeners in the database, and designing a centralized schema for the database that includes how start game IDs are assigned and how end game winners are assigned. This logic is handled by the local machines concurrently, but by using listeners to udpate local machine instance variables, we are able to be confident in the values of local instance variables with minimal latency considerations given the speed of Firebase's realtime database.
* Note: we have a lot of duplicated control structures in the listeners because of the unique limitations posed by firebase listeners. Using the same code to attach multiple listeners resulted in a barrage of errors, so using duplciated code for each listener was our best bet for making it work.

### Model:

* Information about the game and its state is stored in `ElementInformationBundle`. This includes the grid and the game elements like `Avatar` and `DataCube`.
* `CommandExecutor` parses a list of `CommandBlock` objects from the frontend in order to be able to execute commands on avatars. The actual command objects are generated using reflection, and these commands are independently executed on each avatar.
* Each command is structured using `Commands` and other abstract subclasses, which allow the same type of parameters between different types of commands to be used as the input to the command's execution, as well as shared methods between different types of commands in order to provide the possibility of simple extensions.
    * Commands act upon the list of avatars and datacubes and manipulate their information stored in `ElementInformationBundle`.
* When the state of a game element is change, this update information is passed back to the frontend through `ModelController`.


### Frontend:

* The front-end is broken into many classes. Each class has a specific role to either implement a specific section of the screen or to help assist the adding of elements to that screen.
* The two core classes in the front-end are the `ScreenCreator` class and the `LevelView` class. The `ScreenCreator` class is in charge of setting what screen is displayed, such as the start menu, level view, etc. The `LevelView` contains all of the view elements of a level, like the `CodeArea` and `Board`.
* These two classes are connected with the `ViewController`, which handles interfacing the front-end with the back-end. Some of these interactions include loading a new level, sending and receiving program updates for multiplayer, and executing commands.
* There are many non-level screens, including the `StartMenu`, `MatchIdSelector`, `GameTypeSelector`, `TeamSelector`, and `LevelSelector`, and the class names are pretty indicative of what each screen represents.
* The `CodeArea` within the `LevelView` is the part of the screen where the player adds, modifies, and removes commands to the `ProgramStack`. The `codearea` module contains all of the classes involved in this section, and uses inheritance to account for `CommandBlockHolders` with special properties.
* The `Board` is broken into two layers, which is the tile grid layer and the `SpriteLayer`. The tile grid layer is just the grid of the different types of tiles in the level (floor, hole, etc.). The `SpriteLayer` contains the `Avatars` and `Blocks` and is responsible for updating the positions and properties of all these sprites.
* The last two sections of the `LevelView` are the `ControlPanel` which contains buttons for running code, and the `MenuBar`, which has the pause button.
* The `animation` module takes in the updated information (mainly the updated positions of each element), breaks up the positions into different chunks associated with a unique image to be displayed at each of the positions. `AnimationController` then handles how the processed information would be displayed at what rate according to the different buttons the user clicks.
* Finally there is an `GUIElementInterface` and `GUIElementFactory` that contains information about how to make different JavaFX elements that could appear on the screen. Using this class, the duplicate code for making different elements was removed and condensed into one class.

## Assumptions or Simplifications

Assumptions or Simplifications:
* Instead of displaying three different scores (execution score and two bonus scores), the total score will be displayed for easier communication in multiplayer and display
* The backend and the frontend both know about the avatars but the frontend does not know specifically by what logic the avatars are moved.
* For multiplayer, players will have to enter the same `matchID` and select the same level in order to join the same game.
* For game, we wanted the levels to be challenging by not allowing the players to use sort of `Tell` command to move a specific avatar. They would need to use `If` command instead to move a specific set of avatars. However, if we were to implement the `Tell` command, it would be easy based on our current design since `CommandExecutor` would have to just check if the avatar's ID matches the parameter sent in through `Tell`
* Users would want to try the same level again even if they have solved it in order to get a higher score on the level
* Doing math operations on the blocks updates the number on the block the avatar is holding instead of on the block that is on the ground
* Single player has a time bound by a Java int (2^31 seconds = 68 years), which is essentially unlimited time
* Initially, the `CodeArea` was set up so that the player would be unable to move end if blocks before if blocks, and removing one would remove its respective pair to prevent syntax errors, but with the added complication of reconstructing the `ProgramStack` from multiplayer updates, this functionality needed to be removed, so now it's possible that the player creates "syntax" errors in their code


## Changes from the Plan
Other than the small details of how to implement specific levels and what kind of game we exactly wanted to build.
Unlike the other projects, we were faced with a lot of uncertainty because we were not basing our game on a specific
game that existed before. However, even then, we made sure that our design is able to support any
variation of the coding game we wanted and therefore, the overall design did not change as much, which is
a confirmation that our design is well thought out.


## How to Add New Features

NOTES (DELETE BEFORE SUBMITTING):
* How to add new levels:
    * Create a new `levelX` directory containing a few JSON files (the highest level `levelX.json`, the grid `grid.json`, the commands available to the player `commands.json`, and the start `startState.json` and end `endState.json` states) and fill out information that you want for this level. (See the existing level directories for an example.)
    * These JSON files can contain custom information for each level, like the commands and parameters available to each player, the grid layout, the initial setup of avatars and blocks, the win condition, and the time available for completing the level.
* How to add new commands:
    * If desired, make a new abstract subclass extending either `Commands` or an existing abstract subclass in order to support future similar commands better.
    * Make a new class that extends either `Commands` or an abstract subclass of `Commands`.
    * Implement the abstract `execute` method to do whatever you want on the avatar and its surroundings or properties.
    * Be sure to update the `commands.json` file for whichever level contains this command with the correct parameters so that the backend supports the command using reflection and the proper view is supported in the frontend.
    * If the command requires special parameter selection logic or other non-standard properties in the view (i.e. jump requiring its parameter options to update each time it is moved, or if/end if being added together), then create a subclass of `CommandBlockHolder` to implement this logic, and the respective constructing method in `ProgramStack`
* Database communication
* How to add new color themes:
    * Create a new css file and name it your desired color theme.
    * Copy and paste one of the pre-existing css files into your new css file and change the different parameters and colors to your desired colors
    * Add the name of your css file to `CSSPossibilities.properties`
    * Now you have a new color theme that can be chosen on the start screen