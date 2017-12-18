package com.example.mongo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;

import com.exampe.pokemon.Pokemon;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class PokemonMongo {

	private static Logger log;
	
	public static void main(String[] args) {
		Logger log = Logger.getLogger(PokemonMongo.class.getName());
		
		MongoClient mongo = getClient();
		log.info("Client connected successfully");
		
		MongoDatabase database = mongo.getDatabase("myDb");
		log.info("Database successfully accessed");
		

		log.info("Collection created successfully");
		
		
		MongoCollection<Document> collection = database.getCollection("Pokemon");
		
		createDB();
		
//		collection.insertMany(arg0);
		
		mongo.close();
	}
	
	
	public static MongoClient getClient() {
		return new MongoClient(new ServerAddress("192.168.99.100", 27017), new MongoClientOptions.Builder().build());
	}
	
	public static List<Pokemon> createDB(){
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("C://Users//apbon//Downloads//Happy_JSON.js");
		
		try {
			Pokemon pokemon = mapper.readValue(file, Pokemon.class);
			log.info(pokemon.toString());
			
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	


}
