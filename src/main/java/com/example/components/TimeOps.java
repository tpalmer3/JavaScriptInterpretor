package com.example.components;

import com.eclipsesource.v8.V8Object;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.ScriptRunner;

import java.util.HashMap;

@JSComponent(name="timer")
public class TimeOps extends Thread{

    private static HashMap<String, TimeOps> threads = new HashMap<>();
    private static int counter;

    private TimeOps to;
    private String function;
    private String name;
    private String fullName;
    private int mills;

    private volatile boolean stopping = false;
    private boolean initialized = false;

    public TimeOps() {}

    private TimeOps(String function, String name, String fullName, int mills) {
        this.function = function;
        this.name = name;
        this.fullName = fullName;
        this.mills = mills;
    }

    public void run() {
        while(!stopping) {
            try {
                if (!initialized) {
                    ScriptRunner.runScript("var " + fullName + " = " + function);
                    initialized = true;
                }
                ScriptRunner.runScript(fullName+"()");
                this.sleep(mills);
            } catch (InterruptedException e) {
                System.err.println("Thread Ended: " + e.getMessage());
            }
        }
    }

    @JSRunnable
    public void setInterval(V8Object function, int mills) {
        setNamedInterval(function, mills,""+counter);
    }

    @JSRunnable
    public void setNamedInterval(V8Object function, int mills, String name) {
        String fullName = "Stored_Threads_Thread_"+name+"_Function";
        to = new TimeOps(function.toString(), name, fullName, mills);
        to.start();
        threads.put(name, to);
    }

    @JSRunnable
    public static synchronized void stop(String name) {
        threads.get(name).stopping = true;
    }

    @JSRunnable
    public static synchronized void stopAll() {
        for(TimeOps t: threads.values())
            t.stopping = true;
    }
}
