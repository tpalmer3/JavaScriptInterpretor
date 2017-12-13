package com.example.controllers;

import java.util.TimerTask;

public class TimeRunner extends TimerTask {

	@Override
	public void run() {
		System.out.println(FileRunner.runFileWithReturn( System.getProperty("user.dir")+"\\src\\main\\resources\\hello.js"));
		
	}

}
