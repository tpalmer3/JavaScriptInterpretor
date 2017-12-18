package com.example.pokemon;

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







	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", name=" + name + ", types=" + types + ", stats=" + stats + ", height=" + height
				+ ", weight=" + weight + ", img=" + img + "]";
	}







	private int id;
	private String name;
	private List<Types> types;
	private Stats stats;
	private double height, weight;
	private String img;
	
	public Pokemon() {
		
	}




	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public Pokemon(int id, String name, List<Types> types, Stats stats, double height, double weight, String img) {
		super();
		this.id = id;
		this.name = name;
		this.types = types;
		this.stats = stats;
		this.height = height;
		this.weight = weight;
		this.img = img;
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

