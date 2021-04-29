package ooga.model.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import ooga.model.exceptions.ExceptionHandler;

/**
 * @author billyluqiu
 * Level that initailizes a player in Firebase and lets Database know player exists for the game.
 * Assumes nothing regarding player, but teamID must be 1 or 2
 * Assumes instantiated firebase instance, and depends on firebase modules in pom.xml
 *
 */
public class PlayerInitialization {

  private final String rootDBPath;
  private int playerID;
  private boolean errorOccured = false;

  /**
   * Constructor that creates instance
   * Assumes matchID is correct and int value
   * Throws exceptionhandler if player unable to be added to a game,
   * because too many people have joined or other database issues.
   * @param matchID ID of match
   * @param teamID team Number (1 or 2).
   *
   */
  public PlayerInitialization(int matchID, int teamID) {
    rootDBPath = "match_info/match" + matchID + "/team" + teamID + "/";
    if (teamID == 0) {
      this.playerID = 0;
    } else {
      checkGameStatus();
    }
  }

  private void checkGameStatus() {
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    CountDownLatch done = new CountDownLatch(2);
    try {
      final String[] json = {""};
      ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          if (object == null) {
            setUpNewLevel(done);
          } else {
            json[0] = new Gson().toJson(object);
            attemptToAddPlayerToLevel(json[0], done);
          }
          done.countDown();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          // Code (no exceptions thrown as exceptions handled in catch statement)
        }
      });
      done.await();
    } catch (Exception e) {
      throw new ExceptionHandler("exception generating new player");
    }
  }

  private void setUpNewLevel(CountDownLatch done) {
    Map<String, List<Integer>> players = new HashMap<>();
    players.put("playerIDs", Collections.singletonList(1));
    this.playerID = 1;
    setPlayersInDB(done, players);

  }

  private void setPlayersInDB(CountDownLatch done, Map<String, List<Integer>> players) {
    try {
      DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(rootDBPath);
      dataRef.setValue(players, (databaseError, databaseReference) -> done.countDown());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void attemptToAddPlayerToLevel(String json, CountDownLatch done) {
    try {
      List<Integer> playerIDs = (List) new ObjectMapper().readValue(json, Map.class)
          .get("playerIDs");
      if (playerIDs.size() != 1) {
        errorOccured = true;
        System.out.println("error occured");
        done.countDown();
      } else {
        Map<String, List<Integer>> players = new HashMap<>();
        players.put("playerIDs", Arrays.asList(1, 2));
        this.playerID = 2;
        setPlayersInDB(done, players);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Determine if error occured while generating player ID
   * @return true if error occured
   */
  public boolean getErrorOccurred() {
    return this.errorOccured;
  }

  /**
   * Returns playerID (1 or 2)
   * @return int of player ID for current player
   */
  public int getPlayerID() {
    return this.playerID;
  }

}
