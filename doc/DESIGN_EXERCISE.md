# OOGA Lab Discussion
## Names and NetIDs

David Li (dl303)
Harrison Huang (hlh38)
Ji Yun Hyo (jh160)
Billy Luqiu (wyl6)
Kathleen Chen (kc387)


## Fluxx

### High Level Design Ideas
Have a controller initiate and launch the app. Model calculates the scores according to the rules of the game and send the necessary information to be displayed through the controller. Game class in the model keeps the rules of the game and allows the user to change the goal of the game. The Goal class keeps a list of keepers. Individual Player objects execute the playing decisions. For each iteration, the Game class checks to see if there is a winner.

### CRC Card Classes

This class's purpose or value is to keep track the current goal and rules:
```java
 public class Game {
     public void changeGoal(Goal goal)
     public void addRule(Rule rule)
 }
```

This class's purpose or value is be a goal:
```java
 public class Goal {
     public Collection<Keeper> keepersToWin()
 }
```

### Use Cases

* A new game is started with five players.
 ```java
 game.newGame(5);
 ```

* A player plays a new rule card
 ```java
 game.addRule(rule);
 ```

* A player plays a goal card and someone wins because he has the keeprs
```java
player.playCard(goal);
Player winner = game.checkForWin();
if (winner != null) {
    game.playerHasWon(winner);
}
```
 