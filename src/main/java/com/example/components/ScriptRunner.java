package com.example.components;

import com.eclipsesource.v8.*;
import com.example.annotations.JSRunnable;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class ScriptRunner {

    private static ScriptRunner runner = new ScriptRunner();

    private static Logger log;

    private static V8 v8;

    private ScriptRunner() {
        log = Logger.getLogger(ScriptRunner.class.getName());
        v8 = V8.createV8Runtime();
        this.register(Console.class);
    }

    public ScriptRunner getScriptRunner() {return runner;}

    public void register(Class c) {register(c, c.getSimpleName().toLowerCase());}

    public void register(Class c, String name) {
        V8Object obj = new V8Object(v8);
        v8.add(name, obj);
        log.debug(c.getName() + " start registration as: " + name);

        try {
            Object o = c.newInstance();

            for (Method m : c.getMethods()) {
                if(m.isAnnotationPresent(JSRunnable.class)) {
                    obj.registerJavaMethod(o, m.getName(), m.getName(), m.getParameterTypes());
                    log.debug("method: \"" + m.getName() + "\" \n\twith parameters: (" + m.getParameterTypes() +")\n\thas been registered for object: " + name);
                }
            }

        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(InstantiationException e) {
            e.printStackTrace();
        }
        obj.release();
        log.debug(c.getName() + " has been registered as: " + name);
    }

    public static void testV8() {
        v8.executeScript("console.log('hello, world');");
        v8.executeScript("console.log(console.add(1,2));");
        System.out.println(v8.executeScript("'Hello, World!';"));
    }

    @RequestMapping(path="/run_script/{fileName}")
    public static void runScript(@PathVariable String s) {
    }

    public static void main(String args[]) {testV8();}
}
