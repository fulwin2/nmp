package hello;
import java.io.*;
import org.joda.time.LocalTime;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;


import java.util.Arrays;

import com.mongodb.client.MongoClients;

import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.InsertManyOptions;

import org.bson.Document;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;


public class HelloWorld {
  private static void spin(int milliseconds) {
    long sleepTime = milliseconds*1000000L; // convert to nanoseconds
    long startTime = System.nanoTime();
    while ((System.nanoTime() - startTime) < sleepTime) {}
  }
  public static void main(String[] args) {

    final int NUM_TESTS = 1000;
    long start = System.nanoTime();
    /*
    for (int i = 0; i < NUM_TESTS; i++) {
        spin(500);
    }
    */

    /*System.out.println("Took " + (System.nanoTime()-start)/1000000 +
        "ms (expected " + (NUM_TESTS*500) + ")");
    */
    
    char[] pwd = "1".toCharArray();
    MongoCredential credential = MongoCredential.createCredential("devops", "admin", pwd);
    
    MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017),
                                           Arrays.asList(credential));
   /*
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    boolean auth = database.authenticate("devops", "1");
   */
    DB database = mongoClient.getDB("tutorial");
    DBCollection collection = database.getCollection("nubers");
    
    LocalTime currentTime = new LocalTime();
    System.out.println("The current local time is: " + currentTime);
    Greeter greeter = new Greeter();
    while(true) {
     //System.out.println(greeter.sayHello());
     DBObject query = new BasicDBObject("num", 71);
     DBCursor cursor = collection.find(query);
     //MongoCursor<Document> cursor = iterable.iterator();
     while (cursor.hasNext()) {
        System.out.println(cursor.next());
     }
/*     FileInputStream fis = null;
     try {
         fis = new FileInputStream("B:/myfile.txt");
     } catch (FileNotFoundException e) {
         e.printStackTrace();
     }
*/

    }
  }
}
