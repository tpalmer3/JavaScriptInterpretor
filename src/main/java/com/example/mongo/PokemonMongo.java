package com.example.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.emory.mathcs.backport.java.util.Arrays;



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
		
		
		
		collection.insertMany(createDB());
		
		   FindIterable<Document> iterDoc = collection.find();
		   int i = 1;
		   
		   Iterator it = iterDoc.iterator();
		   
		   while(it.hasNext()) {
			   System.out.println(it.next());
			   i++;
		   }

		
//		collection.insertMany(arg0);
		
		mongo.close();
	}
	
	
	public static MongoClient getClient() {
		return new MongoClient(new ServerAddress("192.168.99.100", 27017), new MongoClientOptions.Builder().build());
	}
	
	public static List<Document> createDB(){
		ObjectMapper mapper = new ObjectMapper();
		List<Document> list = new ArrayList<Document>();

		File file = new File("C://Users//apbon//Downloads//Happy_JSON.js");

		
		
		try {
			List<Pokemon> pokemon = mapper.reader().forType(new TypeReference<List<Pokemon>>() {}).readValue(file);
			int length = pokemon.size();
			for(int i = 0; i< length-1; i++) {
				Document document2 = new Document();
				document2.put("id", pokemon.get(i).getId());
				document2.put("name", pokemon.get(i).getName());
				document2.put("types", pokemon.get(i).getTypes().toString());
				document2.put("stats", pokemon.get(i).getStats().toString());
				document2.put("height",pokemon.get(i).getHeight());
				document2.put("weight", pokemon.get(i).getWeight());
				document2.put("img", pokemon.get(i).getImg());
				System.out.println(document2);
				list.add(document2);
				
			}


			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			
		}

		
		
		return list;
	}
	


}
