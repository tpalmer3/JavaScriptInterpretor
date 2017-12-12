package com.example.controllers;

import java.util.Scanner;

public class CLI {

    public static void run() {
        boolean first = true;
        String input = "", output;
        Scanner in = new Scanner(System.in);

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
                    System.err.println(e.getMessage());
                }
                first = true;
                input = "";
            }
        }
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
                dquote_flag = true;
            }
        }
        return (count<=0);
    }

}
