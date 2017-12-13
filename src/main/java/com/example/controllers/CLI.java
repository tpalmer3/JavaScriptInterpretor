package com.example.controllers;

import com.example.components.TimeOps;

import java.sql.Time;
import java.util.Scanner;

public class CLI {

    private static Scanner in;

    public static Scanner getScanner() {return in;}

    public static void run() {
        boolean first = true;
        String input = "", output;
        in = new Scanner(System.in);

        while(true) {
            if(first)
                System.out.print(">> ");
            else
                System.out.print(".. ");
            first = false;

            input += " " + in.nextLine().trim();
            input = input.trim();

            if(input.equals("exit()") || input.equals("exit();"))
                break;

            if(balanced(input)) {
                try {
                    output = ScriptRunner.runScriptWithReturn(input);
                    if (!output.equals("undefined"))
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
        TimeOps.stopAll();
    }

    private static boolean balanced(String s) {
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