package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent(name = "math")
public class MathOps {

    @JSComponent
    public double pi = 3.141592658979323846;

    @JSRunnable
    public double add(double a, double b) {return a+b;}
    @JSRunnable
    public double subt(double a, double b) {return a-b;}
    @JSRunnable
    public double mult(double a, double b) {return a*b;}
    @JSRunnable
    public double div(double a, double b) {return a/b;}
}
