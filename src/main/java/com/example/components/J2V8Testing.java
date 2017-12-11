package com.example.components;

import com.eclipsesource.v8.*;

public class J2V8Testing {

	public static void main(String[] args) {
		V8 runtime = V8.createV8Runtime();
		runtime.executeVoidScript(""
				+ "var person = {};\n"
				+ "var hockeyTeam = {name: 'Wolfpack'};\n"
				+ "person.first = 'Andrew';\n"
				+ "person.last = 'Bonds';\n"
				+ "person.hockeyTeam = hockeyTeam;\n");
		
		
		V8Object person = runtime.getObject("person");
		V8Object hockeyTeam = person.getObject("hockeyTeam");
		System.out.println(hockeyTeam.getString("name"));
		runtime.executeScript("console.log('hello, world');");
        runtime.executeScript("console.log(console.add(1,2));");
        System.out.println(runtime.executeScript("math.pi;"));
        System.out.println(runtime.executeScript("math.add(1,2);"));
        System.out.println(runtime.executeScript("'Hello, World!';"));
		person.release();
		hockeyTeam.release();
		runtime.release();

	}

}
