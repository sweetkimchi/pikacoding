## Features
* Sprint 1 Features: 

   * Frontend:
       * Display screen
           * Level area (human, datacube, boundaries/walls, obstacles?)
           * Command building area (basic drag-and-drop, or other)
           * Available commands
           * Control buttons (play, stop, reset, step?)
           * basic animation
       * Options
           * color scheme

    * Backend:
        * Construct basic grid containing empty spaces, a space with a datacube (holding a set value), a space with a uniquely identified human
            * Consider other types of spaces (walls, holes, other obstacles)
        * Create basic commands and command structure able to be parsed
        * Support all of the functions/buttons in the frontend (play/stop/reset, animation speed)
        * Be able to import one level from a data file with data file encompassing all aspects of the game

* Sprint 2 Features: 

    * Frontend:
        * Boot screen
            * Start menu
            * Select level
            * Load level
        * Display screen
            * Display goal at beginning
            * Smoother animations
            * Smoother drag-and-drop feature
        * Options menu (available from menu and ingame)
            * theme (setting, humans, objects, etc.)

    * Backend:
        * Implement a scoring system based on time, lines of code/number of commands used, etc.
        * Saving and loading states from a file
        * Flesh out more complex commands
        * Determine whether the player has satisfied the goal of the level
        * Have fully functioning levels from start to finish
        * Implement error-checking both for command syntax and whether the commands make sense
            * Can a player actually pick up a datacube in front of it? Is that datacube actually present?
        * Get started on multiplayer?
            * Send data to server and read data from server initially, without multiplayer
            * 

* Complete Sprint Features
    * Front End
        * Polishing animations and drag-and-drop

    * Back End
        * Multiplayer
            * Different players get different features/commands
        * Database
            * Store user data on the web (information on what level they're on, what score they get)
        * Some more commands


## Team Responsibilities
- David Li 
- Harrison Huang 
- Kathleen Chen 
- Ji Yun Hyo 
- Billy Luqiu 
