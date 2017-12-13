package com.example.components;

import com.eclipsesource.v8.V8Object;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;

import java.util.Scanner;

@JSComponent
public class Console {


    Scanner in;
//    @JSRunnable
//    public void log(final String message) {
//        System.out.println("> " + message);
//    }

    @JSRunnable
    public void log(Object out) {
        print("> " + out);
    }

    @JSRunnable
    public void print(Object out) {
        System.out.println(" " + out.toString());
    }

    @JSRunnable
    public String input(String out) {
        in = CLI.getScanner();
        System.out.print(out);
        return in.nextLine().trim();
    }

    @JSRunnable
    public void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    @JSRunnable
    public String add(int a, int b) { return Integer.toString(a+b); }
    

}
