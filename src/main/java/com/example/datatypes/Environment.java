package com.example.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Environment {
    ArrayList<Object> code;
    HashMap<String, Object> env = new HashMap<>();
    Environment parent;
    String type = "Variable";

    public Environment() {}

    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Environment(Environment parent, ArrayList<Object> code) {
        this.parent = parent;
        this.code = code;
    }

    public Environment(Environment e, Environment parent) {
        this.parent = parent;
        this.code = new ArrayList<>();
        for(Object o: e.getCode()) {
            if(o instanceof Environment)
                this.code.add(new Environment((Environment)o, e));
            else
                this.code.add(o);
        }
        this.type = e.getType();
    }

    public void setCode(ArrayList<Object> code) {this.code = code;}

    public ArrayList<Object> getCode() {return this.code;}

    public void setType(String type) {this.type = type;}

    public String getType() {return type;}

    public void put(String k, Object o) {env.put(k,o);}

    public Object get(String k) {
        Object o = env.get(k);
        if(o == null && parent != null) {
            return parent.get(k);
        }
        return o;
    }

    public Object getTopToken() {return code.get(0);}

    public Environment getParent() {return parent;}

    public String toString() {
        String ret = "";
        for(Object o: code)
            if(o instanceof Environment)
                ret += ",{"+((Environment)o).toString()+"}"+((Environment)o).type;
            else
                ret += ","+o.toString();
        return "("+ret.substring(1,ret.length())+")";
    }
}
