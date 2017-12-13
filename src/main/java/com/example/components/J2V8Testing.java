package com.example.components;

import com.eclipsesource.v8.*;
import com.eclipsesource.v8.utils.V8Executor;

public class J2V8Testing {

	public static void main(String[] args) {
		V8Executor executor = new V8Executor("var x = 10;\n"
				+ "x");
		executor.start();
		try {
			executor.join();
			String result = executor.getResult();
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
