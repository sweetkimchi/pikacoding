package ooga.model.database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DatabaseTester {

  public static void main(String[] args) throws IOException, InterruptedException {
    FirebaseService firebaseService = new FirebaseService();
    firebaseService.saveGameLevel(1);
    //MongoDriver mongoDriver = new MongoDriver();

  }
}
