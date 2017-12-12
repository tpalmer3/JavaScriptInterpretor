package com.example.controllers;

import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Scanner;

@RestController
public class FileRunner {

    private static Scanner in;
    private static BufferedWriter out;

    @RequestMapping(path="/run_file/{fname}")
    public static void runFile(@PathVariable String fname) {
        runFileWithReturn(fname);
    }

    @RequestMapping(path="/run_file_with_return/{fname}")
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

    @RequestMapping(path="savetest/{fname}")
    public void saveTest(@PathVariable String fname, @RequestParam String text) {
        System.out.println("SaveTestInit");
        File f = new File(fname+".js");
        try {
            out = new BufferedWriter(new FileWriter(f));
            out.write(text);
            out.newLine();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null)
                in.close();
        }
    }

    @RequestMapping(path="/save_file/{fname}",method= RequestMethod.POST)
    public void saveFile(@PathVariable String fname, @RequestBody String text) {
        File f = new File(fname+".js");
        try {
            out = new BufferedWriter(new FileWriter(f));
            out.write(text);
            out.newLine();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null)
                in.close();
        }
    }
}
