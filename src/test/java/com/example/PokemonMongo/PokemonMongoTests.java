package com.example.PokemonMongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import com.example.mongo.PokemonMongo;
import com.example.pokemon.Pokemon;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;



public class PokemonMongoTests {

	PokemonMongo poke = new PokemonMongo();
	
	MongoClient mongo = poke.getClient();
	MongoDatabase database = mongo.getDatabase("myDb");
	MongoCollection<Document> collection = database.getCollection("Pokemon");
	
	Pokemon testPokemon = new Pokemon();
	
	@Test
	public void updateName() {
		String expectedPass = "MewTwo";
		String expectedFail = "MewThree";
		poke.updateName(collection, 150, "MewTwo");
		
		Bson query = new BasicDBObject("id", 150);
		FindIterable<Document> cursor = collection.find(query);
		String actual = cursor.first().getString("name");
		System.out.println(actual.getClass());

		assertEquals(expectedPass,actual);
		assertNotEquals(expectedFail, actual);
	}
	@Test
	public void updateTypes() {
		
		Bson query = new BasicDBObject("id", 2);
		FindIterable<Document> cursor = collection.find(query);
		String types = cursor.first().getString("types");
		System.out.println(types);

	}
	
	@Test
	public void updateHeight() {
		Double expectedPass = 10.50;
		Double expectedFail = 100.50;
		
		poke.updateHeight(collection, 2, 10.50);
		
		Bson query = new BasicDBObject("id", 2);
		FindIterable<Document> cursor = collection.find(query);
		Double actual = cursor.first().getDouble("height");
		System.out.println(actual);
		
		assertEquals(expectedPass,actual);
		assertNotEquals(expectedFail,actual);
	}
	
	@Test
	public void updateWeight() {
		Double expectedPass = 50.00;
		Double expectedFail = 100.00;
		
		poke.updateWeight(collection, 2, 50.00);
		
		Bson query = new BasicDBObject("id", 2);
		FindIterable<Document> cursor = collection.find(query);
		Double actual = cursor.first().getDouble("weight");
		System.out.println(actual);
		
		assertEquals(expectedPass,actual);
		assertNotEquals(expectedFail,actual);
		
	}
	
	
	
}
