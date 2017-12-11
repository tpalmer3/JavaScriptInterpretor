package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

@JSComponent
public class Console {

    @JSRunnable
    public void log(final String message) {
        System.out.println("> " + message);
    }

    @JSRunnable
    public String add(int a, int b) { return Integer.toString(a+b); }

}
