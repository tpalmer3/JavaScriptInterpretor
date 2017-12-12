package com.example.components;


import java.util.ArrayList;
import java.util.List;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "compare")
public class ComparisonOps {

	
	//Sort the values
	@JSRunnable
	public List<Integer> sortValues(int ... a) {
		int[] list = a;
		List<Integer> intList = new ArrayList<Integer>();
		for(int l: list) {
			intList.add(l);
		}
		intList.stream().distinct();
		
		return null;
	}
	
	
	
}
