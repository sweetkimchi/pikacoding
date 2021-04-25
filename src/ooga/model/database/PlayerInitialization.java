package ooga.model.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import io.opencensus.stats.Aggregation.Count;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class PlayerInitialization {

  private String rootDBPath;
  private int playerID;
  private boolean errorOccured = false;
  public PlayerInitialization(int matchID, int teamID) {
    rootDBPath = "match_info/match"+matchID+"/team"+teamID+"/";
    if (teamID == 0)  {
      this.playerID = 0;
      resetSinglePlayer();
    }
    else  {
      checkGameStatus();
      System.out.println("finished parsing");
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
            System.out.println("HERE");
            setUpNewLevel(done);
          }
          else  {
            System.out.println("Here2");
            System.out.println(json[0]);
            json[0] = new Gson().toJson(object);
            attemptToAddPlayerToLevel(json[0], done);
          }
          done.countDown();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          // Code
        }
      });
      done.await();
    }
    catch (Exception e) {
    }
  }

  private void setUpNewLevel(CountDownLatch done)  {
    Map<String, List<Integer>> players = new HashMap<>();
    players.put("playerIDs", Arrays.asList(1));
    this.playerID = 1;
    setPlayersInDB(done, players);

  }

  private void setPlayersInDB(CountDownLatch done, Map<String, List<Integer>> players) {
    try{
      DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(rootDBPath);
      dataRef.setValue(players, (databaseError, databaseReference) -> {
        done.countDown();
        System.out.println("here");
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void attemptToAddPlayerToLevel(String json, CountDownLatch done)  {
    System.out.println(json);
    try{
      List<Integer> playerIDs = (List) ((Map)new ObjectMapper().readValue(json, Map.class)).get("playerIDs");
      if (playerIDs.size() != 1) {
        errorOccured = true;
        System.out.println("error occured");
        done.countDown();
      }
      else {
        Map<String, List<Integer>> players = new HashMap<>();
        players.put("playerIDs", Arrays.asList(1, 2));
        this.playerID = 2;
        setPlayersInDB(done, players);
      }
    }
    catch(Exception e)  {
      e.printStackTrace();
    }
  }

  private void resetSinglePlayer()  {
    try{
      CountDownLatch done = new CountDownLatch(1);
      DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(rootDBPath);
      dataRef.setValue(null, (databaseError, databaseReference) -> {
        done.countDown();
      });
      done.await();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean getErrorOccurred()  {
    return this.errorOccured;
  }

  public int getPlayerID()  {
    return this.playerID;
  }

}
