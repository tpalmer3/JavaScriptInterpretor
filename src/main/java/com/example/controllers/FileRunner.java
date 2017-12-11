package com.example.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@RestController
public class FileRunner {

    private static Scanner in;

    @RequestMapping(path="/run_file/{fname}")
    public static void runFile(@PathVariable String fname) {
        runFileWithReturn(fname);
    }

    @RequestMapping(path="/run_file/{fname}")
    public static String runFileWithReturn(@PathVariable String fname) {
        File f = new File(fname);

        try {
            in = new Scanner(f);
            String full = "";

            while(in.hasNext())
                full += in.nextLine() + "\n";

            in.close();

            return ScriptRunner.runScriptWithReturn(full);
        } catch(FileNotFoundException e ) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        return "";
    }
}
