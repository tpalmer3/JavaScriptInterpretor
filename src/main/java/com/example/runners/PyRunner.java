package com.example.runners;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import org.python.antlr.base.mod;
import org.python.core.CompilerFlags;
import org.python.core.PythonCodeBundle;
import org.python.core.PythonCompiler;
import org.python.util.PythonInterpreter;

//import org.eclipse.jgit.api.

@JSComponent
public class PyRunner implements ScriptRunner {

    private static PyRunner runner = new PyRunner();
    private static PythonInterpreter interpreter = new PythonInterpreter();

    private static FileRunner fr = new FileRunner(runner);

    private String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\python\\";

    static {
        runner.runFile("setup.py");
    }

    private PyRunner() {}

    public static PyRunner getRunner() {return runner;}

    @JSRunnable
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    @JSRunnable
    public String run(String input) {
        if(input.equals("exit()"))
            System.exit(0);
        interpreter.exec(input);
        return null;
    }

    @Override
    public String run(String input, boolean b) {
        return run(input);
    }

    @JSRunnable
    public void runFile(String fname) {
        fr.runFile(workingDir+fname);
//        interpreter.execfile(workingDir+fname);
    }

    @JSRunnable
    public void start() {
        new CLI(runner, "PY> ").run();
    }

    public static void main(String args[]) {
        runner.start();
    }

}