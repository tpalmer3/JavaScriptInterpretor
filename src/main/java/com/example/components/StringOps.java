package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "string")
public class StringOps {
	
	@JSRunnable
	public int length(String s) {return s.length();}
	
	@JSRunnable
	public String substring(String s, int start, int end) {return s.substring(start, end);}
	
	@JSRunnable
	public String upper(String s) {return s.toUpperCase();}
	
	@JSRunnable
	public String lower(String s) {return s.toLowerCase();}
	
	@JSRunnable
	public String reverse(String s) {
		StringBuilder reversedString = new StringBuilder(s);
		reversedString.reverse();
		String newString = reversedString.toString();
		return newString;
	}
	
	

}
