package com.example.components;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8TypedArray;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "compare")
public class ComparisonOps {


	//Sort the values
	@JSRunnable
	public List<Integer> sortValues(V8Array a) {//V8TypedArray a) {
		int[] list = a.getIntegers(0, a.length());
		List<Integer> intList = new ArrayList<Integer>();
		for(int l: list) {
			intList.add(l);
		}
		intList.stream().distinct();

		return null;
	}

	//Find max value
	@JSRunnable
	public int max(V8Array a) {
		int[] list = a.getIntegers(0, a.length());
		System.out.println("----->" + Arrays.asList(list).toString());
		int max = list[0];

		for(int l: list) {
			if(max < l)
				max = l;
		}
		return max;
	}

}
