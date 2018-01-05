package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.runners.RubyRunner;

@JSComponent(name = "ruby")
public class RubyOps {

    @JSRunnable
    public String run(String fname) {
        return RubyRunner.getRunner().runFile(fname);
    }

    @JSRunnable
    public String exec(String script) {
        return RubyRunner.getRunner().run(script);
    }

}
