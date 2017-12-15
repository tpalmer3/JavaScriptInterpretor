package com.example.datatypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dummy {

    private Class c;
    private String method;

    public Dummy(Class c, String name, String method) {
        this.c = c;
        this.method = method;
    }

    public String toString(Object... vals) {
        Object ret = null;

        try {
            Method m = c.getDeclaredMethod(method);
            ret = m.invoke(null, vals);
        } catch(NoSuchMethodException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not properly instantiated!");
        } catch(IllegalAccessException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not invoked properly!");
        } catch(InvocationTargetException e) {
            System.err.println("Method \"" + c.getSimpleName() + "." + method + "\" not invoked properly!");
        }

        return ret.toString();
    }
}