package com.example.scala;

import com.github.javafaker.Faker;

public class ScalaOps implements MyTrait {

    private String s;

    public void run() {
        System.out.println(MyTrait$class.upperTraitName(this));
    }

    @Override
    public String traitName() {
        return s;//""+((Integer)null).parseInt("25");
    }

    @Override
    public String upperTraitName() {
        return null;
    }

    public static void main(String args[]) {
        ScalaOps ops = new ScalaOps();
        Faker f = new Faker();
        for(int i = 0; i < 50; i++) {
            ops.s = f.bothify("The number of the day is: ###\n\t and the letter combination of the day is: ???");
            ops.run();
        }
    }
}
