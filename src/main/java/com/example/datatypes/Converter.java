package com.example.datatypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Converter<X> {

    private Object object;
    private String method;

    public Converter(Object object, String method) {
        this.object = object;
        this.method = method;
    }

    public String toString(Object... vals) {
        X ret = null;
        Method m;

        try {
            m = object.getClass().getDeclaredMethod(method);
            ret = (X)m.invoke(object, vals);
        } catch(NoSuchMethodException e) {
            System.err.println("Method \"" + object.getClass().getSimpleName() + "." + method + "\" not properly instantiated!");
        } catch(IllegalAccessException e) {
            System.err.println("Method \"" + object.getClass().getSimpleName() + "." + method + "\" not invoked properly!");
        } catch(InvocationTargetException e) {
            System.err.println("Method \"" + object.getClass().getSimpleName() + "." + method + "\" not invoked properly!");
        }

//        if(ret instanceof Long)
//            return ((Long)ret).toString();
        return ret.toString();
    }
}
