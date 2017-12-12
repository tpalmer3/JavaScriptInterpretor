package com.example.controllers;

import java.util.Scanner;

public class CLI {

    public static void run() {
        String input, output;
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print(">> ");
            input = in.nextLine().trim();

            if(input.equals("exit()") || input.equals("exit();"))
                break;

            try {
                output = ScriptRunner.runScriptWithReturn(input);
                if(!output.equals("undefined"))
                    System.out.println(output);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
