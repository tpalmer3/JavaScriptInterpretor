package com.example.controllers;

import com.example.runners.JavaScriptRunner;
import com.example.runners.LispRunner;
import com.example.runners.ScriptRunner;

import java.util.Scanner;

public class CLI {

    private ScriptRunner runner;
    private String tag;

    private static Scanner in;

    public static Scanner getScanner() {return in;}

    public CLI() {}

    public CLI(ScriptRunner runner, String tag) {
        this.runner = runner;
        this.tag = tag;
    }

    public void run() {
        boolean first = true;
        String input = "", output;
        in = new Scanner(System.in);

        if(runner instanceof JavaScriptRunner) {
            System.out.println("Working Directory (variable dir) => " + System.getProperty("user.dir") + "\\src\\main\\resources\\js\\\")");
            System.out.println(new FileRunner(runner).runFileWithReturn(System.getProperty("user.dir") + "\\src\\main\\resources\\js\\setup.js"));
            ((JavaScriptRunner)runner).runScript("setup(\"" + System.getProperty("user.dir").replaceAll("\\\\", "\\\\\\\\") + "\\\\src\\\\main\\\\resources\\\\js\\\\\");");
        } else if(runner instanceof LispRunner) {
            ((LispRunner)runner).setDir(System.getProperty("user.dir") + "\\src\\main\\resources\\lisp\\");
        }

        while(true) {
            if(first)
                System.out.print(tag);
            else
                System.out.print(".. ");
            first = false;

            input += " " + in.nextLine().trim();
            input = input.trim();

            if(balanced(input)) {
                try {
                    output = runner.run(input);
                    if (output != null && !output.equals("undefined") && !output.equals("nil"))
                        System.out.println(output);
                } catch (RuntimeException e) {
                    System.err.print(e.getCause() + " : " + e.getMessage() + "\n______________________________\n");
                    for(StackTraceElement ste : e.getStackTrace())
                        System.err.println(ste.toString());
                }
                first = true;
                input = "";
            }
        }
    }

    private boolean balanced(String s) {
        int count = 0;
        boolean squote_flag = true;
        boolean dquote_flag = true;
        for(char c : s.toCharArray()) {
            if(c == '(' || c == '{' || c == '[')
                count++;
            else if(c == ')' || c == '}' || c == ']')
                count--;

            if(c == '"' && dquote_flag) {
                count++;
                dquote_flag = false;
            } else if(c == '"' && !dquote_flag) {
                count--;
                dquote_flag = true;
            }

            if(c == '\'' && squote_flag) {
                count++;
                squote_flag = false;
            } else if(c == '\'' && !squote_flag) {
                count--;
                squote_flag = true;
            }
        }
        return (count<=0);
    }
}