## ooga API Changes
### Team Three
### Names
* David Li (dl303)
* Harrison Huang (hlh38)
* Ji Yun Hyo (jh160)
* Billy Luqiu (wyl6)
* Kathleen Chen (kc387)

### Changes to the Initial APIs

#### Backend External

 * Method changed: `getChangedStates` changed to updating sprites individually

   * Why was the change made?
       * As the commands are being run and updating sprites, since each command just modifies one sprite at a time, it's more convenient to use a method in the controller that tells the view to update just one sprit
   * Major or Minor (how much they affected your team mate's code)
       * Somewhat major

   * Better or Worse (and why)
       * Better: better aligned with how commands are executed, and more explicit information is able to be passed to the view by updating sprites individually rather than collectively


#### Backend Internal

 * Deprecation of `Grid.java` interface, merge into `InformationBundle.java` interace:

   * Why was the change made?
       * We changed how we thought about the way game elements were stored in the backend. The original plan was more grid-focused, where game elements didn't care about their location on the grid, while we shifted to a more element-centric method.

   * Major or Minor (how much they affected your team mate's code)
       * Major

   * Better or Worse (and why)
       * Better: it removed what would have been a big responsibility of such a grid class to perform actions, and this removal allowed commands to be more functional and useful for us.


 * Removal of `step` method from `Avatar` interface:

   * Why was the change made?
       * The same function was implemented in a type of `BasicCommands`, the `Step` class.

   * Major or Minor (how much they affected your team mate's code)
       * Minor

   * Better or Worse (and why)
       * Better: it adheres better to the single responsibility principle as the commands are better suited for handling the movement of game elements.


#### Frontend External

 * Method changed:`parseAndExecuteCommands` split into `parseCommands` and `executeNextCommand`

   * Why was the change made?
       * Since we wanted the player to be able to pause their command execution and step through the program line by line, it makes more sense to parse the program once at the beginning and iteratively execute each command one at a time.

   * Major or Minor (how much they affected your team mate's code)
       * Somewhat major

   * Better or Worse (and why)
       * Better: the new design allows for implementation of the animation and stepping through commands, while the old design doesn't. It also adheres to the single responsibility principle since each new method is responsible for only one thing.


#### Frontend Internal

 * Method changed: `ControlPanel` changed from having multiple methods for setting each of the button actions to one method `setButtonAction` that takes the button name and the button action as parameters

   * Why was the change made?
       * This removes code duplication and allows for more flexibility if new buttons are added

   * Major or Minor (how much they affected your team mate's code)
       * Minor

   * Better or Worse (and why)
       * Better: better code design (for the reasons stated above)