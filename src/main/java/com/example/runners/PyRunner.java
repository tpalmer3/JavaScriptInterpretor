package com.example.runners;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import org.python.util.PythonInterpreter;

@JSComponent
public class PyRunner implements ScriptRunner {

    private static PyRunner runner = new PyRunner();
    private static PythonInterpreter interpreter = new PythonInterpreter();

    private PyRunner() {}

    public static PyRunner getRunner() {return runner;}

    @Override
    @JSRunnable
    public String run(String input) {
        interpreter.exec(input);
        return null;//interpreter.eval(input).toString();
    }

    @Override
    public String run(String input, boolean b) {
        return null;
    }

    @JSRunnable
    public void runFile(String fname) {
        interpreter.execfile(fname);
    }

    @JSRunnable
    public void start() {
        new CLI(runner, "PY> ").run();
    }

    public static void main(String args[]) {
        runner.start();
    }

}
