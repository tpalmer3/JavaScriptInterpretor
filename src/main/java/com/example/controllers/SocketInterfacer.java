package com.example.controllers;

import com.example.runners.JavaScriptRunner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketInterfacer {

    public static void main(String args[]) throws IOException{
        Socket py = new Socket("127.0.0.1", 8080);
        System.setOut(new PrintStream(py.getOutputStream()));
        CLI.setScanner(new Scanner(py.getInputStream()));
        new CLI(JavaScriptRunner.getRunner(), "JS> ").run();
    }

}
