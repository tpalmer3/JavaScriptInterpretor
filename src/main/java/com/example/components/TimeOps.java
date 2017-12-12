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

    TimeOps to;
    String function;
    String name;
    int mills;

    public TimeOps() {}

    private TimeOps(String function, String name, int mills) {
        this.function = function;
        this.name = name;
        this.mills = mills;
    }

    public void run() {
        while(true) {
            try {
                ScriptRunner.runScript("var " + name + " = " + function);
                ScriptRunner.runScript(name+"()");
                System.out.println(function);
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
        to = new TimeOps(function.toString(), "Stored_Threads_Thread_"+name+"_Function", mills);
        to.start();
        threads.put(name, to);
    }

    @JSRunnable
    public static void stop(String name) {
        threads.get(name).interrupt();
    }
}
