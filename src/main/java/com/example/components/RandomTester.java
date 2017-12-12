package com.example.components;

import java.util.Random;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "random")
public class RandomTester {
	
	
	
	
	@JSRunnable
	public long fillArray(int a) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		int[] list = new int[a];
		for(int i = 0; i < list.length - 1; i++) {
			while(list[i] == 0) {
				int x = rand.nextInt();
				if(x > 50000)
					list[i] = x;
				else
					list[i] = 0;
			}
		}
		long end = System.currentTimeMillis();
		long difference = (start - end)/1000;
		
		return difference;
	}
	
}
