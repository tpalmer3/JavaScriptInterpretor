package com.example.controllers;

import com.eclipsesource.v8.*;
import org.springframework.web.bind.annotation.RequestMapping;

public class ScriptRunner {

    private static V8 v8 = V8.createV8Runtime();
    
    @RequestMapping(path="/run_script/")
    public static void runScript(String s) {
        //Console console = new Console();
        //V8Object v8Console = new V8Object(v8);
        //v8.add("console", v8Console);
        //v8Console.registerJavaMethod(console, "log", "log", new Class<?>[] { String.class });
        //v8Console.release();
        //v8.executeScript("console.log('hello, world');");

        System.out.println(v8.executeScript("'Hello, World!';var x = readline(); x;"));
    }

    public static void main(String args[]) {runScript("");}
}
