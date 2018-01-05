package com.example.runners;

import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import org.jruby.Ruby;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

public class RubyRunner implements ScriptRunner {

    private static String dir = System.getProperty("user.dir") + "\\src\\main\\resources\\ruby\\";

    private static ScriptingContainer ruby = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
    private static RubyRunner runner = new RubyRunner();

    private static FileRunner fileRunner = new FileRunner(runner);

    static {
        System.out.println("Ruby Setup Started");
        runner.runFile("setup.rb");
//        runner.runFile("test.rb");
        System.out.println("Ruby Setup Complete");
    }

    private RubyRunner() {}

    public static RubyRunner getRunner() {return runner;}

    @Override
    public String run(String input) {
        if(input.trim().equals("exit()"))
            System.exit(0);
        try {
            return ruby.runScriptlet(input).toString();
        } catch (NullPointerException e) {}
        return null;
    }

    @Override
    public String run(String input, boolean b) {
        return run(input);
    }

    public String runFile(String fname) {
        return fileRunner.runFileWithReturn(dir+fname);
    }

    public static void main(String args[]) {
        new CLI(runner, "RB> ").run();
    }
}
