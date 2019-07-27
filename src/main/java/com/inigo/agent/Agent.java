package com.inigo.agent;

import javassist.ClassPool;

import java.lang.instrument.Instrumentation;


public class Agent {
    public static void premain(String args, Instrumentation instrumentation){
        ClassLogger transformer = new ClassLogger(buildTransformer(), "com/inigo");
        instrumentation.addTransformer(transformer);
    }

    public static ClassPool buildTransformer(){
        return ClassPool.getDefault();
    }
}
