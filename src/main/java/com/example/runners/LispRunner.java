package com.example.runners;

import com.eclipsesource.v8.V8ScriptExecutionException;
import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.controllers.CLI;
import com.example.controllers.FileRunner;
import com.example.datatypes.Environment;

import java.util.ArrayList;
import java.util.Arrays;

@JSComponent(name="lisp")
public class LispRunner implements ScriptRunner {
    private static Environment globals = new Environment();

    private static String[] parse(String txt) {
        return txt.split(" ");
    }

    private static LispRunner runner = new LispRunner();

    private String workingDir = System.getProperty("user.dir") + "\\src\\main\\resources\\lisp\\";

    private LispRunner() {}

    @JSRunnable
    public String run(String input) {return begin(input);}

    public String run(String input, boolean b) {return begin(input);}

    public static LispRunner getRunner() {return runner;}

    @JSRunnable
    public void start() {new CLI(getRunner(), "LISP> ").run();}

    @JSRunnable
    public String runFile(String fname) {return new FileRunner(runner).runFileWithReturn(workingDir+fname);}

    @JSRunnable
    public void setDir(String workingDir) {this.workingDir = workingDir;}

    @JSRunnable
    public String begin(String txt) {
        ArrayList<String> tokens = new ArrayList<>();
        boolean flag = false;
        String quote = null;

        txt = txt.replaceAll("\\(", "( ").replaceAll("\\)", " )");

        //Quote Joiner
        for(String s: parse(txt)) {
            if(flag) {
                quote += s + " ";
                if(s.charAt(s.length()-1) == '"') {
                    tokens.add(quote.substring(1, quote.length()-2));
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

    private static String processToken(Environment env) {return processToken(env, "");}

    private static String processToken(Environment env, Object token) {return processToken(env, env.getCode(), "");}

    private static String processToken(Environment env, ArrayList<Object> tokens, Object token) {
        String ret = null;

//        System.out.println(env);

        while(!tokens.isEmpty()) {
//            System.out.println("ENV> "+env.toString());
            while (token.equals(""))
                token = tokens.remove(0);
            if (token instanceof String) {
                switch (token.toString().toUpperCase()) {
                    case "BEGIN":
                        for(int i = 0; i < tokens.size(); i++)
                            ret = processToken((Environment)tokens.get(i));
                        while(!tokens.isEmpty())
                            tokens.remove(0);
                        break;
                    case "SET":
                        globals.put(tokens.remove(0).toString(), processToken(env));
                        break;
                    case "LET":
                        env.getParent().put(tokens.remove(0).toString(), processToken(env));
                        break;
                    case "DEFUN":
                        Environment function = new Environment();
                        function.setType("Function");
                        globals.put(tokens.remove(0).toString(), function);
                        function.setCode(new ArrayList<Object>(Arrays.asList(tokens.remove(0), tokens.remove(0))));
                        break;
                    case "FUN":
                        Environment functionb = new Environment();
                        functionb.setType("Function");
                        env.getParent().put(tokens.remove(0).toString(), functionb);
                        functionb.setCode(new ArrayList<Object>(Arrays.asList(tokens.remove(0), tokens.remove(0))));
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
                    case "EXIT":
                        System.exit(0);
                        break;
                    case "+":
                        ret = (ret == null) ? String.valueOf(Double.parseDouble(processToken(env))) : String.valueOf(Double.parseDouble(ret) + Double.parseDouble(processToken(env)));
                        break;
                    case "-":
                        ret = (ret == null) ? String.valueOf(Double.parseDouble(processToken(env))) : String.valueOf(Double.parseDouble(ret) - Double.parseDouble(processToken(env)));
                        break;
                    case "*":
                        ret = (ret == null) ? String.valueOf(Double.parseDouble(processToken(env))) : String.valueOf(Double.parseDouble(ret) * Double.parseDouble(processToken(env)));
                        break;
                    case "/":
                        ret = (ret == null) ? String.valueOf(Double.parseDouble(processToken(env))) : String.valueOf(Double.parseDouble(ret) / Double.parseDouble(processToken(env)));
                        break;
                    default:
                        Object s = env.get((String) token);
                        if (s != null) {
                            env.getCode().add(0, s);
                            return processToken(env);
                        } else if (env != globals) {
                            env.getParent().getCode().add(0, token);
                            return processToken(env.getParent());
                        } else {
//                        ret = JavaScriptRunner.runScriptWithReturn((String) token, true);//getVal((String)token).toString();
//                        if (ret.equals("function () { [native code] }")) {
//                            if (tokens.isEmpty())
//                                ret = JavaScriptRunner.runScriptWithReturn((String) token + "()", true);
//                            else
//                                ret = JavaScriptRunner.runScriptWithReturn((String) token + (Environment) tokens.remove(0), true);
//                        } else if(ret.equals("undefined")) {
                            return (String) token;
//                        }
                        }
                }
            } else if (token instanceof Environment) {
                if (((Environment) token).getType().equals("Variable"))
                    ret = processToken((Environment) token);
                else if (((Environment) token).getType().equals("Function")) {
                    ArrayList<Object> parameters = ((Environment) ((Environment) token).getCode().get(0)).getCode();
                    Environment function = new Environment((Environment) ((Environment) token).getCode().get(1), env);
                    Environment input = (Environment) tokens.remove(0);
                    for (int i = 0; i < parameters.size(); i++) {
                        function.put((String) parameters.get(i), processToken(input));
                    }
//                System.out.println("InFunction> " + token);
                    ret = processToken(function);
                }
                token = "";
            } else {
                ret = token.toString();
            }
//            System.out.println("Empty> "+tokens.isEmpty());
//
//            for(Object o: tokens)
//                System.out.println("Objects> "+o);
        }
        if(ret == null)
            ret = "";
        return ret;
    }

    public static void main(String args[]) {
        System.out.println(runner.begin("(BEGIN (PRINT 5)(Defun z (y) (+ y 2))(Defun x (y) (* y z (7)))(- 100 x (3))(- 100 x (3))")); //lisp.begin("(Defun z (y) (+ y 2))(Defun x (y) (* y z (7)))(- 100 x (3)))")
        System.out.println(runner.begin("(- 100 z (8))"));
        System.out.println(runner.begin("(- 100 x (3))"));
        runner.start();
    }

}
