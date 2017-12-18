package com.example.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pokemon.Pokemon;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
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
	
	public static Set<Pokemon> createDB(){
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		Set<Pokemon> list = null;

		File file = new File("C://Users//apbon//Downloads//Happy_JSON.js");
		try {
			Pokemon temp = new Pokemon();
			Pokemon poke = mapper.readValue(file, Pokemon.class);
			temp.setId(poke.getId());
			temp.setName(poke.getName());
			temp.setTypes(poke.getTypes());
			temp.setStats(poke.getStats());
			temp.setHeight(poke.getHeight());
			temp.setWeight(poke.getWeight());
			temp.setImg(poke.getImg());
			System.out.println(temp.toString());
			list.add(temp);
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

		
		
		return list;
	}
	


}
