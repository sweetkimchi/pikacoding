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
import ooga.controller.BackEndExternalAPI;
import ooga.controller.ModelController;
import ooga.model.database.DatabaseListener;
import ooga.view.level.codearea.CommandBlock;

public class CodeAreaParser implements DatabaseListener {

  private BackEndExternalAPI modelController;
  public CodeAreaParser(ModelController modelController)  {
    this.modelController = modelController;
  }

  @Override
  public void codeAreaChanged() {
    int matchID = 0;

    String rootDBPath = "match_info/match"+matchID+"/";
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
          modelController.updateProgram(parseJSONIntoBlocks(json[0]));
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

  private List<CommandBlock> parseJSONIntoBlocks(String json)  {
    try {
      List<CommandBlock> ret = new ArrayList<>();
      List result =
          new ObjectMapper().readValue(json, List.class);
      Map codingArea = (Map) result.get(0);
      List commands = (List) codingArea.get("codingArea");
      for (int i = 1; i < commands.size(); i++) {
        Map commandBlockParams = (Map) commands.get(i);
        ret.add(new CommandBlock((int) commandBlockParams.get("index"),
            (String) commandBlockParams.get("type"), (Map) commandBlockParams.get("parameters")));
      }
      return ret;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
}
