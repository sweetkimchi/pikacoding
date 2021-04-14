package ooga.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvatarData {

  private Map<String, List<Integer>> avatarData;
  private int id;
  private int xCoord;
  private int yCoord;
  private int lineNumber;

  public AvatarData(){
    avatarData = new HashMap<>();
  }

  public void updatePositions(int id, int xCoord, int yCoord, int lineNumber) {
    this.id = id;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.lineNumber = lineNumber;
  }

  public List<Integer> getPositionUpdates(){
    List<Integer> positions = new ArrayList<>();
    positions.add(id);
    positions.add(xCoord);
    positions.add(yCoord);
    return positions;
  }

  public int getCommandLineNumber(){
    return lineNumber;
  }


}
