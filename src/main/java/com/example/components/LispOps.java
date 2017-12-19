package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.datatypes.Environment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

@JSComponent(name="lisp")
public class LispOps {
    private static Environment globals = new Environment();

    private static String[] parse(String txt) {
        return txt.split(" ");
    }

    @JSRunnable
    public static String begin(String txt) {
        ArrayList<String> tokens = new ArrayList<>();
        boolean flag = false;
        String quote = null;

        txt = txt.replaceAll("\\(", "( ").replaceAll("\\)", " )");

        //Quote Joiner
        for(String s: parse(txt)) {
            if(flag) {
                quote += s + " ";
                if(s.charAt(s.length()-1) == '"') {
                    tokens.add(quote.substring(0, quote.length()-1));
                    flag = false;
                }
            } else {
                if(s.charAt(0)=='"') {
                    quote = s + " ";
                    flag = true;
                } else {
                    tokens.add(s);
                }
            }
        }

        globals.setCode(treeify(tokens, globals));

        String ret = null;

        while(!globals.getCode().isEmpty()) {
            ret = processToken(globals);
        }

        return ret;
    }

    private static ArrayList<Object> treeify(ArrayList<String> tokens, Environment env) {
        ArrayList<Object> ret = new ArrayList<>();
        while(!tokens.isEmpty()) {
            String token = tokens.remove(0);
            switch(token) {
                case "(":
                    Environment e = new Environment(env);
                    e.setCode(treeify(tokens, e));
                    ret.add(e);
                    break;
                case ")":
                    return ret;
                case ")(":
                    tokens.add(0, "(");
                    return ret;
                case "":
                    break;
                default:
                    ret.add(token);
            }
        }
        return ret;
    }

    private static String processToken(Environment env) {
        String ret = null;

//        System.out.println(env);

        ArrayList<Object> tokens = env.getCode();

        Object token = "";
        while(token == "" && !tokens.isEmpty())
            token = tokens.remove(0);
        if(token instanceof String) {
            switch (token.toString().toUpperCase()) {
                case "SET":
                    globals.put(tokens.remove(0).toString(), processToken(env));
                    break;
                case "LET":
                    env.getParent().put(tokens.remove(0).toString(), processToken(env));
                    break;
                case "DEFUN":
                    Environment function = new Environment();
                    function.setType("Function");
                    env.getParent().put(tokens.remove(0).toString(), function);
                    function.setCode(new ArrayList<Object>(Arrays.asList(tokens.remove(0),tokens.remove(0))));
                    break;
                case "PRINT":
                    ret = processToken(env);
                    System.out.println(ret);
                    break;
                case "UPPER":
                    ret = processToken(env).toUpperCase();
                    break;
                case "LOWER":
                    ret = processToken(env).toLowerCase();
                    break;
                case "+":
                    ret = String.valueOf(Double.parseDouble(processToken(env)) + Double.parseDouble(processToken(env)));
                    break;
                case "-":
                    ret = String.valueOf(Double.parseDouble(processToken(env)) - Double.parseDouble(processToken(env)));
                    break;
                case "*":
                    ret = String.valueOf(Double.parseDouble(processToken(env)) * Double.parseDouble(processToken(env)));
                    break;
                case "/":
                    ret = String.valueOf(Double.parseDouble(processToken(env)) / Double.parseDouble(processToken(env)));
                    break;
                default:
                    Object s = env.get((String)token);
                    if(s != null) {
                        env.getCode().add(0, s);
                        return processToken(env);
                    } else if(env == globals) {
                        return (String) token;
                    } else {
                        env.getParent().getCode().add(0, token);
                        return processToken(env.getParent());
                    }
            }
        } else if (token instanceof Environment) {
            if (((Environment) token).getType().equals("Variable"))
                ret = processToken((Environment) token);
            else if (((Environment) token).getType().equals("Function")) {
                ArrayList<Object> parameters = ((Environment)((Environment)token).getCode().get(0)).getCode();
                Environment function = new Environment((Environment)((Environment)token).getCode().get(1), env);
                //ArrayList<Object> inputs = ((Environment)tokens.remove(0)).getCode();
                Environment input = (Environment)tokens.remove(0);
                for(int i = 0; i < parameters.size(); i++) {
                    function.put((String)parameters.get(i), processToken(input));
                }
//                System.out.println("InFunction: " + token);
                ret = processToken(function);
            }
        } else {
            ret = token.toString();
        }
        return ret;
    }

    public static void main(String args[]) {
        System.out.println(begin("(Defun z (y) (+ y 2))(Defun x (y) (* y z (7)))(- 100 x (3))(- 100 x (3))")); //lisp.begin("(Defun z (y) (+ y 2))(Defun x (y) (* y z (7)))(- 100 x (3))")
        System.out.println(begin("(- 100 z (8))"));
        System.out.println(begin("(- 100 x (3))"));
    }

}
