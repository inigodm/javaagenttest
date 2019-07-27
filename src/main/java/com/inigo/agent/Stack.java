package com.inigo.agent;

@SuppressWarnings("unused")
public class Stack {

  static StringBuffer indent = new StringBuffer();

  public synchronized static void push() { indent.append(" "); }

  public synchronized static void pop() {
    indent = new StringBuffer(indent.toString().substring(1));
  }

  public synchronized static void log(String string){
    System.out.println(indent + string);
  }

}
