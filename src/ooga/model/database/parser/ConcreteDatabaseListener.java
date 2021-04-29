package ooga.model.database.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.database.DatabaseListener;
import ooga.model.exceptions.ExceptionHandler;
import ooga.view.level.codearea.CommandBlock;


/**
 * @author billyluqiu
 * Purporse: This class implements the databaseListener interface to
 * attach listeners for database changes.
 * Assumptions: Assumes firebase instance has been instantiated, and there is a working internet connection.
 * Assumes that two teams exist in the game, and for levelEnded and codeArea the the teams are 2 people and are playing
 * the game
 * Additionally, assumes database schema is formatted with endpoints specified in the file paths.
 * Example usage: Call codeAreaChange() to attach a listener to see if the code area changes
 * Only created for multiplayer.
 *
 */
public class ConcreteDatabaseListener implements DatabaseListener {

  private final BackEndExternalAPI modelController;
  private final int matchID;
  private final int teamID;
  private boolean currentTeamStarted = false;
  private boolean otherTeamStarted = false;
  private boolean levelEndedForCurrentTeam = false;
  private boolean levelEndedForOtherTeam = false;
  private int scoreForCurrentTeam;
  private int scoreForOtherTeam;
  private final Map<DatabaseReference, ValueEventListener> valueEventListeners = new HashMap<>();
  private List<CommandBlock> lastCommandBlockForCurrentComputer;

  /**
   * Class constructor to set instance variables for database listener
   * @param modelController instance of modelController to call model
   * @param matchID ID of the match
   * @param teamID int for team ID (1 or 2)
   */
  public ConcreteDatabaseListener(ModelController modelController, int matchID, int teamID) {
    this.modelController = modelController;
    this.matchID = matchID;
    this.teamID = teamID;
    lastCommandBlockForCurrentComputer = new ArrayList<>();
  }

  /**
   * Set last command block that is sent up to the DB, assumes well formatted list
   * @param lastCommandBlockForCurrentComputer list of Command Blocks that reflects current code area
   *
   */
  public void setLastCommandBlockForCurrentComputer(
      List<CommandBlock> lastCommandBlockForCurrentComputer) {
    this.lastCommandBlockForCurrentComputer = lastCommandBlockForCurrentComputer;
  }

  /**
   * Creates listener that sees if the code area changes in Firebase, assumptions in class comment
   */
  @Override
  public void codeAreaChanged() {

    String rootDBPath = "match_info/match" + matchID + "/team" + teamID + "/codingArea/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          modelController.receivedProgramUpdate(parseJSONIntoBlocks(json[0]));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
      this.valueEventListeners.put(ref, listener);
    }
    catch (Exception e) {
      throw new ExceptionHandler("error receiving code area changes");
    }
  }

  /**
   * Creates listener that sees if other player on same team finished level, assumptions in class comment
   */
  @Override
  public void checkLevelEndedForCurrentTeam() {
    String rootDBPath = "match_info/match" + matchID + "/team" + teamID + "/gameEnded/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          if (!json[0].equals("null")) {
            int score = getScoreFromJSON(json[0]);
            declareLevelEndedForCurrentTeam(score);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
      this.valueEventListeners.put(ref, listener);
    }
     catch (Exception e) {
      throw new ExceptionHandler("error checking if level has ended");
    }
  }

  private int getScoreFromJSON(String json) {
    try {
      Map jsonMap = new ObjectMapper().readValue(json, Map.class);
      return (Integer) jsonMap.get("score");
    } catch (Exception e) {
      return 0;
    }
  }
  private void declareLevelEndedForCurrentTeam(int score)  {
    this.levelEndedForCurrentTeam = true;
    this.scoreForCurrentTeam = score;
    modelController.notifyCurrentTeamEnded(score);
    if (levelEndedForOtherTeam) {
      levelEndedForBothTeams();
    }
  }

  private void levelEndedForBothTeams() {
    for (DatabaseReference ref : this.valueEventListeners.keySet()) {
      ref.removeEventListener(this.valueEventListeners.get(ref));
    }
    modelController.notifyBothTeamsEnded(this.scoreForCurrentTeam, this.scoreForOtherTeam);
  }

  /**
   * Attaches listener that is triggered when both teams have finished the leve, assumptions same as above
   */
  @Override
  public void checkLevelEndedForBothTeams() {
    int otherTeamID = getOtherTeamID();
    String rootDBPath = "match_info/match" + matchID + "/team" + otherTeamID + "/gameEnded/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          if (!json[0].equals("null")) {
            int score = getScoreFromJSON(json[0]);
            declareLevelEndedForOtherTeam(score);
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      });
      this.valueEventListeners.put(ref, listener);
    }
    catch (Exception e) {
      throw new ExceptionHandler("error checking if level has ended");
    }
  }

  private void declareLevelEndedForOtherTeam(int score) {
    this.levelEndedForOtherTeam = true;
    this.scoreForOtherTeam = score;
    if (this.levelEndedForCurrentTeam) {
      levelEndedForBothTeams();
    }
  }

  /**
   * Attaches listeers that listen to see if all four players are here
   */
  @Override
  public void checkLevelStarted() {
    this.checkOtherTeamAllPresent();
    checkCurrentTeamAllPresent();
  }

  private void checkCurrentTeamAllPresent() {
    String rootDBPath = "match_info/match" + matchID + "/team" + teamID + "/playerIDs/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          try {
            List playerIDs =
                new ObjectMapper().readValue(json[0], List.class);
            if (playerIDs.size() == 2) {
              teamAllHere();
            }
          } catch (IOException e) {
            throw new ExceptionHandler("error checking if all people present");
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      });
      this.valueEventListeners.put(ref, listener);
    } catch (Exception e) {
      throw new ExceptionHandler("error checking if all people present");
    }
  }

  private void checkOtherTeamAllPresent() {

    int otherTeam = getOtherTeamID();
    String rootDBPath = "match_info/match" + matchID + "/team" + otherTeam + "/playerIDs/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ValueEventListener listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          try {
            List playerIDs =
                new ObjectMapper().readValue(json[0], List.class);
            if (playerIDs.size() == 2) {
              otherTeamAllHere();
            }
          } catch (IOException e) {
            throw new ExceptionHandler("error checking if all people present");
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      });
      this.valueEventListeners.put(ref, listener);
    } catch (Exception e) {
      throw new ExceptionHandler("error checking if all people present");
    }
  }

  private int getOtherTeamID() {
    int otherTeam;
    if (teamID == 1) {
      otherTeam = 2;
    } else {
      otherTeam = 1;
    }
    return otherTeam;
  }

  private void teamAllHere() {
    this.currentTeamStarted = true;
    checkBothTeamsHere();
  }

  private void otherTeamAllHere() {
    this.otherTeamStarted = true;
    checkBothTeamsHere();
  }

  private void checkBothTeamsHere() {
    if (this.otherTeamStarted && this.currentTeamStarted) {
      modelController.startGameAfterBothTeamsPresent();
    }
  }

  private List<CommandBlock> parseJSONIntoBlocks(String json) {
    try {
      List<CommandBlock> ret = new ArrayList<>();
      List commands =
          new ObjectMapper().readValue(json, List.class);

      if (commands == null) {
        return new ArrayList<>();
      }
      for (int i = 1; i < commands.size(); i++) {
        Map commandBlockParams = (Map) commands.get(i);
        ret.add(new CommandBlock((int) commandBlockParams.get("index"),
            (String) commandBlockParams.get("type"), (Map) commandBlockParams.get("parameters")));
      }
      if (ret.size() == this.lastCommandBlockForCurrentComputer.size()) {
        for (int i = 0; i < ret.size(); i++) {
          if (!ret.get(i).equals(this.lastCommandBlockForCurrentComputer.get(i))) {
            return ret;
          }
        }
        return null;
      }
      return ret;
    } catch (Exception e) {
      return null;
    }
  }
}
