package com.example.controllers;

import com.eclipsesource.v8.*;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.Set;

@RestController
public class ScriptRunner {

    private static ScriptRunner runner = new ScriptRunner();

    private static Logger log;

    private static V8 v8;

    private ScriptRunner() {
        log = Logger.getLogger(ScriptRunner.class.getName());
        v8 = V8.createV8Runtime();
        this.Initializer("com.example.components");
    }

    public ScriptRunner getScriptRunner() {return runner;}

    public void Initializer(String packageName) {
        Reflections classes = new Reflections(packageName);
        Set<Class<?>> annotated = classes.getTypesAnnotatedWith(JSComponent.class);

        for(Class c : annotated) {
            String name = ((JSComponent)c.getAnnotation(JSComponent.class)).name();
            if(name.equals("[unassigned]")){register(c);}else{register(c, name);};
        }
    }

    public void register(Class c) {register(c, c.getSimpleName().toLowerCase());}

    public void register(Class c, String name) {
        V8Object obj = new V8Object(v8);
        v8.add(name, obj);
        log.debug(c.getName() + " start registration as: " + name);

        try {
            Object o = c.newInstance();

            for (Method m : c.getDeclaredMethods()) {
                if(m.isAnnotationPresent(JSRunnable.class)) {
                    obj.registerJavaMethod(o, m.getName(), m.getName(), m.getParameterTypes());
                    log.debug("method: \"" + m.getName() + "\" \n\twith parameters: (" + m.getParameterTypes() +")\n\thas been registered for object: " + name);
                }
            }

            for (Field f : c.getDeclaredFields()) {
                if(f.isAnnotationPresent(JSComponent.class)) {
                    Class c2 = f.getType();
                    Object val = f.get(o);
                    if(c2 == String.class)
                        obj.add(f.getName(), val.toString());
                    else if(c2.isPrimitive() && val instanceof Number)
                        obj.add(f.getName(), (Double)val);
                    else if(c2.isPrimitive() && val instanceof Boolean)
                        obj.add(f.getName(), (Boolean)val);
                    log.debug("field: \"" + f.getName() + "\"\n\tWith a value of: " + val.toString() + "\n\thas been registered for object: " + name);
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
        runScript("console.log('hello, world');");
        runScript("console.log(console.add(1,2));");
        System.out.println(runScriptWithReturn("math.pi;"));
        System.out.println(runScriptWithReturn("math.add(1,2);"));
        System.out.println(runScriptWithReturn("'Hello, World!';"));
        System.out.println(runScriptWithReturn("advMath.square(10);"));
        System.out.println(runScriptWithReturn("advMath.sqrt(100);"));
        System.out.println(runScriptWithReturn("advMath.pow(2,3);"));
        System.out.println(runScriptWithReturn("string.length('Test');"));
        System.out.println(runScriptWithReturn("string.substring('Testing',1,5);"));
        System.out.println(runScriptWithReturn("string.upper('test');"));
        System.out.println(runScriptWithReturn("string.lower('TEST');"));
        System.out.println(runScriptWithReturn("string.reverse('Test');"));
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(FileRunner.runFileWithReturn( System.getProperty("user.dir")+"\\src\\main\\resources\\test.js"));
    }

    @RequestMapping(path="/run_script/{script}")
    public static void runScript(@PathVariable String s) {
        v8.executeScript(s);
    }

    @RequestMapping(path="/run_script_with_return/{script}")
    public static String runScriptWithReturn(@PathVariable String s) {
        return v8.executeScript(s).toString();
    }

    public static void main(String args[]) {testV8();}
}
