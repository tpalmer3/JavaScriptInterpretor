package com.example.mongo;

import java.util.Iterator;
import java.util.logging.Logger;


import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class ConnectToDb {
	
	private static Logger log;

	public static void main(String[] args) {
		log = Logger.getLogger(ConnectToDb.class.getName());
		// Creating a Mongo client 
		
		
		MongoClient mongo = new MongoClient(new ServerAddress("192.168.99.100", 27017), new MongoClientOptions.Builder().build());

		// Creating Credentials 
		MongoCredential credential; 
		credential = MongoCredential.createCredential("sampleUser", "myDb", 
				"password".toCharArray()); 
		log.info("Connected to the database successfully");  

		//Accessing the database 
		MongoDatabase database = mongo.getDatabase("myDb");  
	    System.out.println("Credentials ::"+ credential); 
		//Creating a collection 
	    log.info("Collection created successfully");
	    
	    for(String name: database.listCollectionNames()) {
	    	System.out.println(name);
	    }
	    
	    MongoCollection<Document> collection = database.getCollection("sampleCollection");
	    log.info("Collection samepleCollect selected successfully");
		
//	    Document document = new Document("title", "MongoDB")
//	    		.append("id", 1)
//	    		.append("description", "database")
//	    		.append("likes", 100)
//	    		.append("url", "http://www.tutorialspoint.com/mongodb/")
//	    		.append("by", "tutorials point");
//	    collection.insertOne(document);
//	   log.info("Document inserted successfully");
//	    
//	    collection.updateOne(Filters.eq("id", 1), Updates.set("likes", 150));
//	    log.info("Document update successfully...");
//	    
//	    collection.deleteOne(Filters.eq("id",1));
//	    log.info("Document deleted successfully");
	    
//	   MongoCollection<Document> collection2 = database.getCollection("sampleCollection2");
//	   collection2.drop();
//	   log.info("Collection successfully dropped...");
	   
	   FindIterable<Document> iterDoc = collection.find();
	   int i = 1;
	   
	   Iterator it = iterDoc.iterator();
	   
	   while(it.hasNext()) {
		   System.out.println(it.next());
		   i++;
	   }
	   
	   mongo.close();

		
		
	}

}
