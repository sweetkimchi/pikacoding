package ooga.model.database.parser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.concurrent.CountDownLatch;
import ooga.model.database.DatabaseListener;

public class CodeAreaParser implements DatabaseListener {

  @Override
  public void codeAreaChanged() {
    int matchID = 0;

    String rootDBPath = "match_info/match"+matchID+"/";
    DatabaseReference ref = FirebaseDatabase.getInstance()
        .getReference(rootDBPath);
    try {
      //CountDownLatch done = new CountDownLatch(1);
      final String[] json = {""};
      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          Object object = dataSnapshot.getValue(Object.class);
          json[0] = new Gson().toJson(object);
          System.out.println(json[0]);
          //done.countDown();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          System.out.println("The read failed: " + databaseError.getCode());
        }
      });
      //done.await();
    }
    catch (Exception e) {

    }
  }
}
