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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.database.DatabaseListener;
import ooga.view.level.codearea.CommandBlock;

public class ConcreteDatabaseListener implements DatabaseListener {

  private BackEndExternalAPI modelController;
  private int matchID;
  private int teamID;
  private boolean currentTeamStarted = false;
  private boolean otherTeamStarted = false;
  private boolean levelEndedForCurrentTeam = false;

  private List<CommandBlock> lastCommandBlockForCurrentComputer;
  public ConcreteDatabaseListener(ModelController modelController, int matchID, int teamID)  {
    this.modelController = modelController;
    this.matchID = matchID;
    this.teamID = teamID;
    lastCommandBlockForCurrentComputer = new ArrayList<>();
  }


  public void setLastCommandBlockForCurrentComputer(
      List<CommandBlock> lastCommandBlockForCurrentComputer) {
    this.lastCommandBlockForCurrentComputer = lastCommandBlockForCurrentComputer;
  }

  @Override
  public void codeAreaChanged() {

    String rootDBPath = "match_info/match"+matchID+"/team"+teamID+"/codingArea/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          modelController.receivedProgramUpdate(parseJSONIntoBlocks(json[0]));
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
    }
    catch (Exception e) {

    }
  }

  @Override
  public void checkLevelEndedForCurrentTeam() {
    String rootDBPath = "match_info/match"+matchID+"/team"+teamID+"/gameEnded/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          if (!json[0].equals("null"))  {
            declareLevelEndedForCurrentTeam();
          }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
    }
    catch (Exception e) {

    }
  }

  private void declareLevelEndedForCurrentTeam()  {
    System.out.println("level ended for current team!!!! ");
    this.levelEndedForCurrentTeam = true;
  }

  @Override
  public void checkLevelEndedForBothTeams() {
    int otherTeamID = getOtherTeamID();
    String rootDBPath = "match_info/match"+matchID+"/team"+otherTeamID+"/gameEnded/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          if (!json[0].equals("null"))  {
            declareLevelEndedForOtherTeam();
          }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
    }
    catch (Exception e) {

    }
  }

  private void declareLevelEndedForOtherTeam() {
    if (this.levelEndedForCurrentTeam)  {
      System.out.println("level ended for both teams!!!!!!!");
    }
  }

  @Override
  public void checkLevelStarted() {
    this.checkOtherTeamAllPresent();
    checkCurrentTeamAllPresent();
  }

  private void checkCurrentTeamAllPresent() {
    String rootDBPath = "match_info/match"+matchID+"/team"+teamID+"/playerIDs/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      final boolean[] allPlayersPresent = {false};
      var listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          try {
            List playerIDs =
                new ObjectMapper().readValue(json[0], List.class);
            System.out.println("player IDs" + playerIDs);
            if (playerIDs.size() == 2)  {
              teamAllHere();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
    }
    catch (Exception e) {
    }
  }

  private void checkOtherTeamAllPresent()  {

    int otherTeam = getOtherTeamID();
    String rootDBPath = "match_info/match"+matchID+"/team"+otherTeam+"/playerIDs/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      final String[] json = {""};
      final boolean[] allPlayersPresent = {false};
      var listener = ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          try {
            List playerIDs =
                new ObjectMapper().readValue(json[0], List.class);
            //System.out.println(playerIDs);
            if (playerIDs.size() == 2)  {
              otherTeamAllHere();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
    }
    catch (Exception e) {
    }
  }

  private int getOtherTeamID() {
    int otherTeam = 0;
    if (teamID == 1)  {
      otherTeam = 2;
    }
    else  {
      otherTeam = 1;
    }
    return otherTeam;
  }

  private void teamAllHere()  {
    this.currentTeamStarted = true;
    checkBothTeamsHere();
  }

  private void otherTeamAllHere() {
    this.otherTeamStarted = true;
    checkBothTeamsHere();
  }

  private void checkBothTeamsHere() {
    if (this.otherTeamStarted && this.currentTeamStarted) {
      System.out.println("both teams are here lol");
    }
  }

  private List<CommandBlock> parseJSONIntoBlocks(String json)  {
    try {
      List<CommandBlock> ret = new ArrayList<>();
      List commands;
      try {
        commands =
            new ObjectMapper().readValue(json, List.class);
      }
      catch (Exception e)  {
        return null;
      }
      //System.out.println("new PARSED COMMANDS" + commands);
      if (commands == null) return null;
      for (int i = 1; i < commands.size(); i++) {
        Map commandBlockParams = (Map) commands.get(i);
        ret.add(new CommandBlock((int) commandBlockParams.get("index"),
            (String) commandBlockParams.get("type"), (Map) commandBlockParams.get("parameters")));
      }
      System.out.println(this.lastCommandBlockForCurrentComputer);
      System.out.println(ret);
      if (ret.size() == this.lastCommandBlockForCurrentComputer.size()) {
        for (int i = 0; i < ret.size(); i++)  {
          System.out.println(ret.get(i).getIndex() + " " + this.lastCommandBlockForCurrentComputer.get(i).getIndex());
          if (!ret.get(i).equals(this.lastCommandBlockForCurrentComputer.get(i)))  {
            System.out.println(i);

            System.out.println(ret.get(i).getIndex() + " " + lastCommandBlockForCurrentComputer.get(i).getIndex());
            System.out.println(ret.get(i).getType() + " " + lastCommandBlockForCurrentComputer.get(i).getType());
            System.out.println(ret.get(i).getParameters() + " " + lastCommandBlockForCurrentComputer.get(i).getParameters());


            return ret;
          }
        }
        return null;
      }

      return ret;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
}
