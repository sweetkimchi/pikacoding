package ooga.model.database.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.web.HTMLEditorSkin.Command;
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.database.DatabaseListener;
import ooga.view.level.codearea.CommandBlock;

public class ConcreteDatabaseListener implements DatabaseListener {

  private BackEndExternalAPI modelController;
  private int matchID;
  private int teamID;
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
          System.out.println(json[0]);
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
  public void checkLevelEnded() {

  }

  @Override
  public void checkLevelStarted() {

  }

  private List<CommandBlock> parseJSONIntoBlocks(String json)  {
    try {
      List<CommandBlock> ret = new ArrayList<>();
      List commands =
          new ObjectMapper().readValue(json, List.class);
      if (commands == null) return null;
      for (int i = 1; i < commands.size(); i++) {
        Map commandBlockParams = (Map) commands.get(i);
        ret.add(new CommandBlock((int) commandBlockParams.get("index"),
            (String) commandBlockParams.get("type"), (Map) commandBlockParams.get("parameters")));
      }
      if (ret.size() == this.lastCommandBlockForCurrentComputer.size()) {
        for (int i = 0; i < ret.size(); i++)  {
          if (!ret.get(i).equals(this.lastCommandBlockForCurrentComputer.get(i)))  {
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
