package com.example.runners.drools;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.runners.ScriptRunner;
import org.drools.core.WorkingMemory;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@JSComponent
public class DroolsRunner implements ScriptRunner {

    private static WorkingMemory mem;

    private static KieServices services;
    private static KieFileSystem fs;
    private static KieContainer container;
    private static KieSession session;

//    private String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\runners\\drools\\";
    private String workingDir = "\\com\\example\\runners\\drools\\";

    private static DroolsRunner runner = new DroolsRunner();

    private DroolsRunner() {
        services = KieServices.Factory.get();
        fs = services.newKieFileSystem();
    }

    public static DroolsRunner getRunner() {
        return runner;
    }

    @JSRunnable
    public void setup(String rule) throws FileNotFoundException {
        fs.write(ResourceFactory.newClassPathResource(workingDir+rule));

        KieBuilder kieBuilder = services.newKieBuilder(fs).buildAll();

        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new FileNotFoundException();
        }

        container = services.newKieContainer(services.getRepository().getDefaultReleaseId());
        session = container.newKieSession();
    }

    public void insert(Object o) {
        session.insert(o);
        //session.setGlobal("", o);
    }

    @JSRunnable
    public void execute() {
        session.fireAllRules();
    }

    @JSRunnable
    @Override
    public String run(String input) {
        try {
            setup(input);
            execute();
        } catch(IOException e) {
            System.err.println(e.getCause() + " : " + e.getMessage());
        }
        return null;
    }

    @Override
    public String run(String input, boolean b) {
        return run(input);
    }
}
