# DEMO VIDEO OUTLINE

Describe how we will 

Screenshare all four screens and a firebase schema view at the same time so he can see databse updating in real time


### Functionality

Show off the features of your running program:

* run the program from the master branch through a planned series of steps/interactions that shows at least the following features:
    * choosing a game to play, playing a game, winning or losing a game, choosing a different game to play and starting that one
        * do one level
        * change to different level
    * choosing different color themes and languages
        * lol do languages work... no so just change the color
    * if possible, playing two different games at the same time or saving a game and restarting from that point
        * do two different computers playing on two different match ids?
    * any optional features implemented
        * ***MULTIPLAYER***
    * anything else that makes your project unique and interesting
    * show an example of each kind of data file used by the program and describe which are essential (e.g., internal resources) and which can be user created (e.g., external or example data)
        * json files for levels
        * resource files for front end stuff
        * css for formatting
    * show three examples of making a change in a data file and then seeing that change reflected when the program is run
        * Single player usage
        * Level
        * Adding a color scheme
        * Changing the image for avatar
* show a variety of tests (including both happy and sad paths for both the backend and frontend) and verify they all pass
    * Hope and pray the front end tests will pass
    * Backend tests [Grid.java] backend

### Design

Revisit the design from the original plan and compare it to your current version (as usual, focus on the behavior and communication between modules, not implementation details):

* revisit the design's goals: is it as flexible/open as you expected it to be and how have you closed the core parts of the code in a data driven way?
    * Jiyun can give overview 
    * ![](https://i.imgur.com/WBUZlkT.png)
* describe two APIs in detail (one from the first presentation and a new one):
    * [Billy] talk about listener API for database
    * [Jiyun] controller
    * show the public methods for the API
    * how does it provide a service that is open for extension to support easily adding new features?
    * how does it support users (your team mates) to write readable, well design code?
    * how has it changed during the Sprints (if at all)?
* show two Use Cases implemented in Java code in detail that show off how to use each of the APIs described above
    * Animation use case [David]
    * Starting a single player game 
* describe two designs [Billy]
    * UML diagram consistent [Jiyun]
    * one that has remained stable during the project
        * Parser remained relatively stable during the project
    * one that has changed significantly based on your deeper understanding of the project: how were those changes discussed and what trade-offs ultimately led to the changes
        * Start/end game changed quite a lot for multiplayer with the need for rooms and synched scores 

### Team
Present what your team and, you personally, learned from managing this project:

* contrast the completed project with where you planned it to be in your initial Wireframe and the initial planned priorities and Sprints with the reality of when things were implemented
    * Individually, share one thing each person learned from using the Agile/Scrum process to manage the project.
    * let's go through the list in order, so kathleen next, we should talk less for each bullet
        * Kathleen - I learned that having well defined goals for each sprint allowed us to stay on track. Splitting work into small sprints help make sure we did not procrastinate too much.
        * David - It's important to set clear goals at the beginning of each sprint and divide up tasks evenly so that the most progress is able to be made at each sprint, and so that progress can be easily tracked and monitored throughout the project.
        * Jiyun - I learned that agile process is importance since we have to be able to estimate how much time it would take for us to implement a feature.
        * Harrison - It is best to be flexible and accepting of feedback in case things don't work
        * Billy - how we need to take into account past sprint for future sprint
* show a timeline of at least four significant events (not including the Sprint deadlines) and how communication was handled for each (i.e., how each person was involved or learned about it later)
    * 3/25 deciding what to build, everyone was in zoom room and meeting then 
    * Single level working: 4/14, everyone tested it to make sure they were with happy with it
    * Firebase on 4/17: Communicating need for different firebase keys for everyone on slack
    * 4/25: Final sprint, all met up in person to debug multiplayer and put finishing touches
    * Individually, share one thing each person learned from trying to manage a large project yourselves. 
        * Kathleen - importance of setting internal deadlines; David and I set interanl deadlines and portioned out work for the view and it helped make sure we were on track
        * David - It's important to spend consistent time each day working on the project when possible, especially in a team project, since cramming everything last minute can lead to rushed code and a lack of refactoring.
        * Ji Yun Hyo - Although this is still a small-sized project, it was very interesting to do a project solely online through Zoom. I've learned that we have to know what works for our team.
        * Harrison - It is best to upkeep communication at all times, especially when everybody is working individually
        * Billy - importance of not getting ma at anyone and always looking for the positives
* describe specific things the team actively worked to improve on during the project and one thing that could still be improved [KATHLEEN]
    * better use of slack channel for communication
    * keeping people updated on what each person has done by giving small status updates
    * IMPOREVMENT: don't leave things to be done last minute
    * Individually, share one thing each person learned about creating a positive team culture.
        * Kathleen - having a pre-existing relationship with your team or building a solid team realtionship outside of just coding is important to know how each of your teammates function and better create a positive team culture
        * David - Creating an open and honest environment within the team will lead to the most productivity and lead to less miscommunication and conflicts
        * Ji Yun Hyo - Understand what works for the team, be supportive and helpful
        * Harrison - Be forgiving and always look forward to the next step
        * billy: Be understanding when life happens and a teammate gets sick or something 
* revisit your Team Contract to assess what parts of the contract are still useful and what parts need to be updated (or if something new needs to be added) [Billy]
    * No conflcit resolution needed
    * Policy for keeping each other up to date and schedling was kept
    * Individually, share one thing each person learned about how to communicate and solve problems collectively, especially ways to handle negative team situations.
        * Kathleen - making sure to set clear expectations so that each teammate knows what is expected of them, but also knows how to and feels comfortable enough to communicate if they cannot meet a deadline
        * David - Avoiding passive-aggressiveness and instead being direct (but not accusatory) about issues with teammates or with the team in general
        * Harrison - Keeping people, especially the people you're working closest to, updated on what you're doing is very imporant; being clear about communication expectations
        * Jiyun - Be supportive
        * Billy: always update in advance (oh lol) and not after the face


Single player demo
* Start menu, select theme
    * Language just have to make properties files
* Match id just for multiplayer
* Singleplayer
* Level selector
* Left side is the board
    * What the player controls using commands
    * Top right is goal, explains what the player has to do in the board
* Below is the code area
    * Add commands from the command bank
        * Available commands varies between levels, determined by data files
    * To the right is program, player can move commands and remove commands, and change parameters for the commands
* At the bottom is control panel, used for running the program
    * Avatar and block movements are animated
* Top left is score, based on number of lines of code that the player has run
    * If it reaches 0, the player loses the level and has to try again
    * If the player completes the goal, it shows the win screent
* Commands
    * nearest
    * throw
    * throwOver throws up to 2 tiles in front, ignoring obstaces
    * add/subtract/multiply