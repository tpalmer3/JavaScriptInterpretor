package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "advMath")
public class AdvancedMathOps {

	@JSRunnable
	public double square(double a) {return a*a;}

	@JSRunnable
	public double sqrt(double a) {return Math.sqrt(a);}
	
	@JSRunnable
	public double pow(double a, double b) {return Math.pow(a, b);}
	
	
	
}
