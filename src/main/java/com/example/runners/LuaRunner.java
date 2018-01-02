package com.example.runners;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jme.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@JSComponent(name="lua")
public class LuaRunner implements ScriptRunner{

    Globals globals;

    private static LuaRunner runner = new LuaRunner();
    private static FileRunner frunner = new FileRunner(runner);

    private String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\lua\\";

    private LuaRunner() {
        globals = JmePlatform.standardGlobals();

        try {
            this.register(null, System.class.getMethod("exit", int.class), "exit");
            this.register(this, this.getClass().getMethod("runFile", String.class), "runFile");
            this.register(this, this.getClass().getMethod("setWorkingDir", String.class), "setDir");
            this.register(JavaScriptRunner.getRunner(), JavaScriptRunner.class.getMethod("runScriptWithReturn", String.class), "runJS");
            this.register(LispRunner.getRunner(), LispRunner.class.getMethod("run", String.class), "runLisp");
        } catch(NoSuchMethodException e) {
            System.err.println(e.getCause()+" : "+e.getMessage());
        }

        globals.set("dir", System.getProperty("user.dir") + "\\src\\main\\resources\\lua\\");
    }

    private void register(Object o, Method m, String name) {
        if(m.getParameterTypes().length == 0) {
            ZeroArgFunction funct = new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    try {
                        return LuaString.valueOf(m.invoke(o).toString());
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getCause() + " : " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        System.out.println(e.getCause() + " : " + e.getMessage());
                    }
                    return NIL;
                }
            };
            globals.set(name, funct);
        } else if(m.getParameterTypes().length == 1) {
            OneArgFunction funct = new OneArgFunction() {
                @Override
                public LuaValue call(LuaValue luaValue) {
                    try {
                        Class c = m.getParameterTypes()[0];
//                        System.out.println(c.getSimpleName());
                        switch(c.getSimpleName()){
                            case "String":
                                return LuaString.valueOf(m.invoke(o, luaValue.toString()).toString());
                            case "Integer":
                            case "int":
                                return LuaString.valueOf(m.invoke(o, luaValue.toint()).toString());
                            case "Double":
                            case "double":
                                return LuaString.valueOf(m.invoke(o, luaValue.todouble()).toString());
                            default:
                                return LuaString.valueOf(m.invoke(o, luaValue.touserdata(0)).toString());
                        }

                    } catch (IllegalAccessException e) {
                        System.err.println(e.getCause() + " : " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        System.err.println(e.getCause() + " : " + e.getMessage());
                    }
                    return NIL;
                }
            };
            globals.set(name, funct);
        }// else
    }

    @JSRunnable
    public String run(String input) {
        return globals.load(input).call().toString();
    }

    @JSRunnable
    public String runFile(String fname) {
        return frunner.runFileWithReturn(workingDir+fname);
//        LuaValue ret =  globals.loadfile(workingDir+fname);
    }

    @JSRunnable
    public LuaValue runLib(String fname) {
        LuaValue ret =  globals.load(frunner.loadFile(workingDir+fname)).call();
        if(ret == null)
            return null;
        return ret;
    }

    @JSRunnable
    public void setWorkingDir(String workingDir) {this.workingDir = workingDir;}

    public String run(String input, boolean b) {
        return run(input);
    }

    public static LuaRunner getRunner() {return runner;}

    @JSRunnable
    public void start() { new CLI(runner, "Lua> ").run();}

    public static void main(String args[]) {
        runner.run("local msg = 'Hello, world!'; print(msg)");
        runner.runFile("test.lua");
        runner.runFile("test2.lua");
        runner.runFile("file.lua");
//        System.out.println(runner.runLib("redis.lua")+"_________________________________________________________");
        runner.globals.set("redis", runner.runLib("redis.lua").typename());
//        runner.runFile("redis3.lua");
        new CLI(runner, "Lua> ").run();
    }
}
