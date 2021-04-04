package ooga.model.parser;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class Tester {


  public static void main(String[] args)  {
    JSONParser parser = new JSONParser();
    try {
      String res = System.getProperty("user.dir") + "/data/gameProperties/level1/level_01.json";

      Object obj = parser.parse(new FileReader(res));
      // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
      JSONObject jsonObject = (JSONObject) obj;

      System.out.println(jsonObject.get("numPeople"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
