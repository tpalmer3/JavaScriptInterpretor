package com.example.mongo;

import java.util.Random;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.github.javafaker.Faker;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RandomValueGenerator {

	private static Logger log;

	public static void run() {
		log = Logger.getLogger(RandomValueGenerator.class.getName());
		MongoClient mongo = getClient();
		MongoDatabase database = mongo.getDatabase("myDB");
		log.info("Connected to Database");
		
		MongoCollection<Document> collection = database.getCollection("Test");
		collection.insertOne(populateDatabase());
		log.info(collection.toString());
	}
	
	public static Document populateDatabase() {
		Faker faker = new Faker();
		Random random = new Random();
		String firstname = faker.name().firstName();
		String lastname = faker.name().lastName();
		int age = random.nextInt(120);
		String state = getState();
		int balance = random.nextInt(1000000);
		
		Document document = new Document();
		document.put("First Name", firstname);
		document.put("Last Name", lastname);
		document.put("Age", age);
		document.put("Living State", state);
		document.put("Balance", balance);

		return document;
	}
	
	public static String getState() {
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO","CT","DE","FL","GA","HI","ID","IL"
				,"IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ"
				,"NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
		Random random = new Random();
		int statePicker = random.nextInt(49);
		String selection = new String(states[statePicker]);
		return selection;
	}
	
	public static MongoClient getClient() {
		return new MongoClient(new ServerAddress("192.168.99.100", 27017), new MongoClientOptions.Builder().build());
	}

	public static void main(String[] args) {
		RandomValueGenerator.run();
	}
}
