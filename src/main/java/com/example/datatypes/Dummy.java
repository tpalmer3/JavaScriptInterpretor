package com.example.datatypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dummy {

    private Class c;
    private String method;

    public Dummy(Class c, String method) {
        this.c = c;
        this.method = method;
    }

    public String run(Object[] vals) {
        Object ret = null;
        Method m = null;

        try {
            m = c.getDeclaredMethod(method);
            ret = m.invoke(null, vals);
        } catch(NoSuchMethodException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not properly instantiated!");
        } catch(IllegalAccessException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not invoked properly!");
        } catch(InvocationTargetException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not invoked properly!");
        }
        System.out.println(ret);
        System.out.println(ret.toString());
        System.out.println(m.getReturnType().cast(ret).toString());
        return ret.toString();
    }
}