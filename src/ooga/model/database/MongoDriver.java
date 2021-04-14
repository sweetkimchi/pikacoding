package ooga.model.database;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class MongoDriver {
  MongoClient mongoClient;
  MongoDatabase database;

  public MongoDriver()  {
    String connectionString = "mongodb+srv://bluqiu:EZnxRIJ3E5CcOwOb@cluster0.j2ahm.mongodb.net/test?retryWrites=true&w=majority";
    try (MongoClient mongoClient = MongoClients.create(connectionString)) {
      MongoIterable<String> strings = mongoClient.listDatabaseNames();
      MongoCursor<String> cursor = strings.cursor();

      while (cursor.hasNext())  {
        System.out.println(cursor.next());
      }
    }


  }


}
