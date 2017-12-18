package com.exampe.pokemon;

import java.util.List;

public class Pokemon {
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<Types> getTypes() {
		return types;
	}



	public void setTypes(List<Types> types) {
		this.types = types;
	}



	public Stats getStats() {
		return stats;
	}



	public void setStats(Stats stats) {
		this.stats = stats;
	}



	public double getHeight() {
		return height;
	}



	public void setHeight(double height) {
		this.height = height;
	}



	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	private int id;
	private String name;
	private List<Types> types;
	private Stats stats;
	private double height, weight;
	private String image;
	
	public Pokemon() {
		
	}
	
	public Pokemon(int id, String name, List<Types> types, Stats stats, double height, double weight, String image) {
		super();
		this.id = id;
		this.name = name;
		this.types = types;
		this.stats = stats;
		this.height = height;
		this.weight = weight;
		this.image = image;
	}



	public enum Types{
		Normal,
		Fight,
		Flying,
		Poison,
		Ground,
		Rock,
		Bug,
		Ghost,
		Steel,
		Fire,
		Water,
		Grass,
		Electric,
		Psychic,
		Ice,
		Dragon,
		Dark,
		Fairy
	}
	
	
}

