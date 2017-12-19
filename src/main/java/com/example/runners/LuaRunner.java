package com.example.runners;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import com.fasterxml.uuid.NoArgGenerator;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jme.*;

@JSComponent
public class LuaRunner implements ScriptRunner{

    Globals globals;

    private static LuaRunner runner = new LuaRunner();

    private String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\lua\\";

    private LuaRunner() {
        globals = JmePlatform.standardGlobals();

//        ZeroArgFunction exit = new ZeroArgFunction() {
//            @Override
//            public LuaValue call() {
//                System.exit(0);
//                return NIL;
//            }
//        };
//        globals.load(exit);
//        globals.add(exit);
    }

    @JSRunnable
    public String run(String input) {
        return globals.load(input).call().toString();
    }

    @JSRunnable
    public String runFile(String fname) {
        return new FileRunner(runner).runFileWithReturn(workingDir+fname);
    }

    public String run(String input, boolean b) {
        return run(input);
    }

    public LuaRunner getRunner() {return runner;}

    public static void main(String args[]) {
        runner.run("local msg = 'Hello, world!'; print(msg)");
        runner.runFile("test.lua");
        runner.runFile("test2.lua");
        runner.runFile("file.lua");
        runner.runFile("redis.lua");
//        runner.runFile("redis3.lua");
        new CLI(runner, "Lua> ").run();
    }
}
