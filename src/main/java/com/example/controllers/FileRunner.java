package com.example.controllers;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.runners.JavaScriptRunner;
import com.example.runners.ScriptRunner;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Scanner;

@RestController
@JSComponent(name="files")
public class FileRunner {

    private static Scanner in;
    private static BufferedWriter out;

    private static ScriptRunner runner;

    public FileRunner() {}

    public FileRunner(ScriptRunner runner) {this.runner = runner;}

    @RequestMapping(path="/run_file/{fname}")
    @JSRunnable
    public void runFile(@PathVariable String fname) {
        runFileWithReturn(fname);
    }

    @JSRunnable
    public String loadFile(String fname) {
        File f = new File(fname);

        try {
            in = new Scanner(f);
            String full = "";

            while(in.hasNext())
                full += in.nextLine() + "\n";

            in.close();

            return full;
        } catch(FileNotFoundException e ) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        return "";
    }

    @RequestMapping(path="/run_file_with_return/{fname}")
    @JSRunnable
    public String runFileWithReturn(@PathVariable String fname) {
            String out = runner.run(loadFile(fname).replaceAll("local ", ""), false);
            if(out.equals("undefined"))
                out = "";

            return out;
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
