package com.example.scala;

public class ScalaOps implements MyTrait {

    public void run() {
        System.out.println(MyTrait$class.upperTraitName(this));
    }

    @Override
    public String traitName() {
        return "";
    }

    @Override
    public String upperTraitName() {
        return "";
    }

    public static void main(String args[]) {
        new ScalaOps().run();
    }
}
