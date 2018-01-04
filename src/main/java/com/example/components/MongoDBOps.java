package com.example.components;

import com.example.annotations.JSRunnable;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Iterator;

//@JSComponent(name="mongo_ops")
public class MongoDBOps {

//    static {
//        LogManager.getLogger("org.mongodb.driver.connection").setLevel(org.apache.log4j.Level.OFF);
//        LogManager.getLogger("org.mongodb.driver.management").setLevel(org.apache.log4j.Level.OFF);
//        LogManager.getLogger("org.mongodb.driver.cluster").setLevel(org.apache.log4j.Level.OFF);
//        LogManager.getLogger("org.mongodb.driver.protocol.insert").setLevel(org.apache.log4j.Level.OFF);
//        LogManager.getLogger("org.mongodb.driver.protocol.query").setLevel(org.apache.log4j.Level.OFF);
//        LogManager.getLogger("org.mongodb.driver.protocol.update").setLevel(org.apache.log4j.Level.OFF);
//    }

    private String host = "192.168.99.100";
    private int port = 27017;

    private MongoClient mongo;// = new MongoClient("192.168.99.100", 27017);//new ServerAddress("192.168.99.100", 27017), new MongoClientOptions.Builder().build());
    private MongoCredential credential;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoClient getMongo() {return mongo;}
    public MongoCredential getCredential() {return credential;}
    public MongoDatabase getDatabase() {return database;}
    public MongoCollection<Document> getCollection() {return collection;}

    @JSRunnable
    public void setup(String host, int port) {
        this.host = host;
        this.port = port;
//        ScriptRunner.getScriptRunner().register(com.mongodb.MongoClient.class, getMongo(), "mongo_client", false);
    }

    @JSRunnable
    public String run(String cmd) {
//        mongo = new MongoClient(host, port);
//        Document ret = database.runCommand(new Bson());
//        mongo.close();
        return cmd;//ret.toString();
    }

    @JSRunnable
    public void login(String user, String password) {
        credential = MongoCredential.createCredential(user, "main", password.toCharArray());
    }

    @JSRunnable
    public void getDatabase(String name) {
        database = mongo.getDatabase(name);
//        ScriptRunner.getScriptRunner().register(com.mongodb.client.MongoDatabase.class, getDatabase(), "mongo_db", false);
    }

    @JSRunnable
    public void getCollection(String name) {
        if(database.getCollection(name) == null)
            database.createCollection(name);
        collection = database.getCollection(name);
//        ScriptRunner.getScriptRunner().register(com.mongodb.client.MongoCollection.class, getCollection(), "mongo_collection", false);
    }

    public static void main(String[] args) {
        MongoDBOps m = new MongoDBOps();
//        m.login("mongo", "mongo");
//        m.getDatabase("main");
//        m.getCollection("TheAwesomeCollection");
//        Document d = new Document().append("id", 1)
//                .append("description", "database")
//                .append("likes", 100)
//                .append("url", "http://www.tutorialspoint.com/mongodb/")
//                .append("by", "tutorials point");
//        m.collection.insertOne(d);
//
//        FindIterable<Document> iterDoc = m.collection.find();
//        int i = 1;
//
//        Iterator it = iterDoc.iterator();
//
//        while (it.hasNext()) {
//            System.out.println(it.next());
//            i++;
//        }
    }
}
