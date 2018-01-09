package com.example.mongo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connection {

	private static Logger log;

	public static void main(String[] args) {
		log = Logger.getLogger(Connection.class.getName());
		// Creating a Mongo client

		InetAddress ip;
		String ipAddress = "";
		try {
			ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();
			log.info(ipAddress);

		} catch (UnknownHostException e) {
			log.warning(e.getMessage());
		}

		MongoClient mongo = new MongoClient(new ServerAddress(ipAddress, 27017),
				new MongoClientOptions.Builder().build());

		// Creating Credentials
		MongoCredential credential;
		credential = MongoCredential.createCredential("sampleUser", "myDb", "password".toCharArray());
		log.info("Connected to the database successfully");

		// Accessing the database
		MongoDatabase database = mongo.getDatabase("myDb");
		System.out.println("Credentials ::" + credential);
		// Creating a collection
		log.info("Collection created successfully");

		for (String name : database.listCollectionNames()) {
			System.out.println(name);
		}

		MongoCollection<Document> collection = database.getCollection("sampleCollection");
		log.info("Collection samepleCollect selected successfully");

		// MongoCollection<Document> collection2 =
		// database.getCollection("sampleCollection2");
		// collection2.drop();
		// log.info("Collection successfully dropped...");

		mongo.close();

	}

}
