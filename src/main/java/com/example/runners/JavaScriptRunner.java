package com.example.runners;

import com.eclipsesource.v8.*;
import com.eclipsesource.v8.utils.V8Executor;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.components.RedisOps;
import com.example.components.TimeOps;
import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.*;
import java.util.*;

@RestController
public class JavaScriptRunner implements ScriptRunner{

    private static JavaScriptRunner runner = new JavaScriptRunner();

    private static Logger log;

    private static V8 v8;

    private static List<String> ignoring;

    private static FileRunner fr;
    private static String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\js\\";

    private JavaScriptRunner() {
        log = Logger.getLogger(JavaScriptRunner.class.getName());

        v8 = V8.createV8Runtime();
        v8.registerV8Executor(new V8Object(v8), new V8Executor(""));

        ignoring = new ArrayList<>(Arrays.asList("com.example.components.RandomTester",
                "com.example.components.MongoDBOps"));
//                "com.example.components.LispOps"));

        V8Object obj = new V8Object(v8);
        v8.add("system", obj);
        JavaCallback jc = new JavaCallback() {
            @Override
            public Object invoke(V8Object v8Object, V8Array v8Array) {
                TimeOps.stopAll();
                System.exit(0);
                return null;
            }
        };
        obj.registerJavaMethod(jc, "exit");

        this.initializer("com.example.components");
        this.initializer("com.example.controllers");
        this.registerStatic(java.lang.Integer.class, "integer", false);
        this.registerStatic(java.lang.Double.class, "double", false);
        this.registerStatic(java.lang.String.class, "string", false);
        this.registerStatic(java.lang.Math.class, "math", false);
        this.register(redis.clients.jedis.Jedis.class, RedisOps.getJedis(), "redis", false);

        this.register(com.example.runners.LuaRunner.class, LuaRunner.getRunner(), "lua", true);
        this.register(com.example.runners.LispRunner.class, LispRunner.getRunner(), "lisp", true);
        this.register(com.example.runners.PyRunner.class, PyRunner.getRunner(), "python", true);

        v8.add("dir", System.getProperty("user.dir")+"\\src\\main\\resources\\");

    }

    public static ScriptRunner getRunner() {return runner;}

    public static void ignore(String className) {ignoring.add(className);}

    public void initializer(String packageName) {
        Reflections classes = new Reflections(packageName);
        Set<Class<?>> annotated = classes.getTypesAnnotatedWith(JSComponent.class);

        for(Class c : annotated) {
            if(!ignoring.contains(c.getName())) {
                String name = ((JSComponent) c.getAnnotation(JSComponent.class)).name();
                if (name.equals("[unassigned]")) {register(c);} else {register(c, name);}
            }
        }
    }

    public void register(Class c) {register(c, c.getSimpleName().toLowerCase());}

    public void register(Class c, String name) {register(c,name,true);}

    public void register(Class c, String name, boolean annotationNeaded) {register(c,null,name,annotationNeaded);}

    public void register(Class c, Object o, String name, boolean annotationNeaded) {
        V8Object obj = new V8Object(v8);
        v8.add(name, obj);
        log.debug(c.getName() + " start registration as: " + name);

        HashMap<String,Integer> counts = new HashMap<>();

        try {
            if(o == null)
                o = c.newInstance();
        } catch(IllegalAccessException e) {
            try {
                for (Constructor con : c.getConstructors()) {
                    con.setAccessible(true);
                    con.newInstance(null);
                }
            } catch (InstantiationException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        for (Method m : c.getDeclaredMethods()) {
            if(m.isAnnotationPresent(JSRunnable.class) || !annotationNeaded) {
                try{
                    m.setAccessible(true);

                    boolean repeat = false;
                    if(counts.get(m.getName()) != null) {
                        counts.put(m.getName(), counts.get(m.getName()) + 1);
                        repeat = true;
                    }

                    if(m.getReturnType() == Long.class) {
                        Object invoker = o;
                        JavaCallback jc = new JavaCallback() {
                            Object obj = invoker;

                            @Override
                            public Object invoke(V8Object v8Object, V8Array v8Array) {

                                Object ret = null;
                                try {
                                    ArrayList<Object> objs = new ArrayList<>();
                                    for(String key: v8Array.getKeys())
                                        objs.add(v8Array.get(key));
                                    ret = m.invoke(this.obj, objs.toArray());
                                } catch(IllegalAccessException e) {

                                } catch(InvocationTargetException e) {

                                }
                                if(ret == null)
                                    return V8Value.NULL;
                                else if(ret instanceof Long || ret instanceof Float)
                                    return ret.toString();
                                else
                                    return ret;
                            }
                        };

                        if(repeat) {
                            obj.registerJavaMethod(jc, m.getName() + counts.get(m.getName()));
                        } else {
                            obj.registerJavaMethod(jc, m.getName());
                            counts.put(m.getName(), 0);
                        }
                    } else {
                        if(repeat) {
                            obj.registerJavaMethod(o, m.getName(), m.getName() + counts.get(m.getName()), m.getParameterTypes());
                        } else {
                            obj.registerJavaMethod(o, m.getName(), m.getName(), m.getParameterTypes());
                            counts.put(m.getName(), 0);
                        }
                    }
                    printMethodDebug(m, name);
                } catch(RuntimeException e) { System.err.println(e.toString()+">"+e.getCause()+":"+e.getMessage()+":"+m.getName());
                }
            }
        }

        for (Field f : c.getDeclaredFields()) {
            if(f.isAnnotationPresent(JSComponent.class) || !annotationNeaded) {
                f.setAccessible(true);
                Class c2 = f.getType();
                Object val = new Object();
                try {
                    val = f.get(o);
                } catch(IllegalAccessException e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                } catch(ExceptionInInitializerError e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                    continue;
                } catch(NullPointerException e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                    continue;
                }
                if(c2 == String.class)
                    obj.add(f.getName(), val.toString());
                else if(c2.isPrimitive() && val instanceof Number) {
                    if(val instanceof Double)
                        obj.add(f.getName(), (Double) val);
                    else if(val instanceof Integer)
                        obj.add(f.getName(), (Integer) val);
                } else if(c2.isPrimitive() && val instanceof Boolean)
                    obj.add(f.getName(), (Boolean)val);
                log.debug("field: \"" + f.getName() + "\"\n\tWith a value of: " + val + "\n\thas been registered for object: " + name);
            }
        }
        obj.release();
        log.debug(c.getName() + " has been registered as: " + name);
    }

    public void registerStatic(Class c, String name, boolean annotationNeaded) {
        V8Object obj = new V8Object(v8);
        v8.add(name, obj);
        log.debug(c.getName() + " start registration as: " + name);

        HashMap<String,Integer> counts = new HashMap<>();

        for (Method m : c.getDeclaredMethods()) {
            if(m.isAnnotationPresent(JSRunnable.class) || !annotationNeaded) {
                try{
                    m.setAccessible(true);
                    boolean repeat = false;
                    if(counts.get(m.getName()) != null) {
                        counts.put(m.getName(), counts.get(m.getName()) + 1);
                        repeat = true;
                    }

                    JavaCallback jc = new JavaCallback() {
                        @Override
                        public Object invoke(V8Object v8Object, V8Array v8Array) {
                            Object ret = null;
                            try {
                                ArrayList<Object> objs = new ArrayList<>();
                                for(String key: v8Array.getKeys())
                                    objs.add(v8Array.get(key));
                                if(Modifier.isStatic(m.getModifiers()))
                                    ret = m.invoke(null, objs.toArray());
                                else
                                    ret = m.invoke(objs.get(0), objs.subList(1, objs.size()-1).toArray());
                            } catch(IllegalAccessException e) {

                            } catch(InvocationTargetException e) {

                            }
                            if(ret == null)
                                return V8Value.NULL;
                            else if(ret instanceof Long || ret instanceof Float)
                                return ret.toString();
                            else
                                return ret;
                        }
                    };

                    if(repeat) {
                        obj.registerJavaMethod(jc, m.getName() + counts.get(m.getName()));
                        System.out.println("function " + name + "." + m.getName() + counts.get(m.getName()) + " = \"" + m.getName() + "\"");
                    } else {
                        obj.registerJavaMethod(jc, m.getName());
                        counts.put(m.getName(), 0);
                        System.out.println("function " + name + "." + m.getName() + " = \"" + m.getName() + "\"");
                    }
                    printMethodDebug(m, name);
                } catch(RuntimeException e) { System.err.println(e.toString()+">"+e.getCause()+":"+e.getMessage()+":"+m.getName());
                }
            }
        }

        for (Field f : c.getDeclaredFields()) {
            if(f.isAnnotationPresent(JSComponent.class) || !annotationNeaded) {
                f.setAccessible(true);
                Class c2 = f.getType();
                Object val = new Object();
                try {
                    val = f.get(null);
                } catch(IllegalAccessException e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                } catch(ExceptionInInitializerError e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                    continue;
                } catch(NullPointerException e) {
                    System.err.println(e.getCause() + " : " + e.getMessage());
                    continue;
                }
                if(c2 == String.class)
                    obj.add(f.getName(), val.toString());
                else if(c2.isPrimitive() && val instanceof Number) {
                    if(val instanceof Double)
                        obj.add(f.getName(), (Double) val);
                    else if(val instanceof Integer)
                        obj.add(f.getName(), (Integer) val);
                } else if(c2.isPrimitive() && val instanceof Boolean)
                    obj.add(f.getName(), (Boolean)val);
                log.debug("field: \"" + f.getName() + "\"\n\tWith a value of: " + val + "\n\thas been registered for object: " + name);
            }
        }
        obj.release();
        log.debug(c.getName() + " has been registered as: " + name);
    }

    private static void printMethodDebug(Method m, String name) {
        String debug = "method: \"" + m.getName() + "\" \n\twith parameters: ( ";
        for(Class cl : m.getParameterTypes())
            debug += cl.getSimpleName()+" ";
        debug += ")\n\thas been registered for object: " + name;

        log.debug(debug);
    }

    public String run(String input) {return runScriptWithReturn(input);}

    public String run(String input, boolean b) {return runScriptWithReturn(input, b);}

    @RequestMapping(path="/run_script/{script}")
    public static void runScript(@PathVariable String s) {
        runScriptWithReturn(s);
    }

    @RequestMapping(path="/run_script_with_return/{script}")
    public static synchronized String runScriptWithReturn(@PathVariable String s) {return runScriptWithReturn(s,true);}

    @RequestMapping(path="/run_script_with_return/{script}")
    public static synchronized String runScriptWithReturn(@PathVariable String s, boolean needsRelease) {
        V8Locker locker = v8.getLocker();
        String ret = "";

        boolean flag = true;
        while(flag) {
            try {

                locker.acquire();
                ret += v8.executeScript(s);
                if(needsRelease)
                    locker.release();

                flag = false;

            } catch(Error e) {}
        }

        return ret;
    }

    public String runFile(String fname) {
        if(fr == null)
            fr =new FileRunner(runner);
        return fr.runFileWithReturn(workingDir+fname);
    }

    public static Object getVal(String key) {return v8.get(key);}

    public static void main(String args[]) {
        runner.runFile("setup.js");
//        runner.run("setInterval(function a() {python.start();}, 100);",true);
        new CLI(runner, "JS> ").run();
    }
}
