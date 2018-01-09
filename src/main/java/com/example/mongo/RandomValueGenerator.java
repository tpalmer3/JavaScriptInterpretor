package com.example.mongo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.github.javafaker.Faker;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RandomValueGenerator extends TimerTask {

	private static Logger log;
	
	public static void main(String[] args) {
		TimerTask timerTask = new RandomValueGenerator();
		Timer timer = new Timer();
		Random random = new Random();
		timer.scheduleAtFixedRate(timerTask, 0, random.nextInt(10000));
	}
	
	public void run() {
		log = Logger.getLogger(RandomValueGenerator.class.getName());
		MongoClient mongo = getClient();
		log.info("Connected to Mongo");
		MongoDatabase database = mongo.getDatabase("myDB");
		log.info("Connected to myDB");
		
		MongoCollection<Document> collection = database.getCollection("TestCollection");
		log.info("Connected to the collection " + collection.getNamespace());
		Document document = populateDatabase();
		collection.insertOne(document);
		log.info(collection.toString());

		mongo.close();
	}
	
	public static Document populateDatabase() {
		log.info("In the populateDatabase method");
		Faker faker = new Faker();
		
		
		Random random = new Random();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		String firstname = faker.name().firstName();
		String lastname = faker.name().lastName();
		int age = random.nextInt(120);
		String state = getState();
		int balance = random.nextInt(1000000);
		String dateAdded = dtf.format(now);
		
		
		Document document = new Document();
		document.put("First Name", firstname);
		document.put("Last Name", lastname);
		document.put("Age", age);
		document.put("Living State", state);
		document.put("Balance", balance);
		document.put("Date Added", dateAdded);
		
		log.info(document.toString());
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
		return new MongoClient("localhost", 27017);
		//return new MongoClient(new ServerAddress("192.168.56.100", 27017), new MongoClientOptions.Builder().build());
	}

}
