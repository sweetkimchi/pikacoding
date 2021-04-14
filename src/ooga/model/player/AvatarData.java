package ooga.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvatarData extends ElementData{

  private Map<String, List<Integer>> avatarData;
  private int id;
  private int xCoord;
  private int yCoord;
  private int lineNumber;

  public AvatarData(){
    super();
  }

  public int getCommandLineNumber(){
    return lineNumber;
  }


}
