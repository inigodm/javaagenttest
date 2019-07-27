package com.inigo.agent;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello from CallSpy!");
    System.out.println("Usage: ");
    System.out.println("   java -DXbootclasspath/p:/home/inigo/projects/testing/test/lib/javaassit.jar:/home/inigo/projects/testing/agent/build/libs/agent-1.0-SNAPSHOT.jar");
    System.out.println(" -javaagent:/home/inigo/projects/testing/agent/build/libs/agent-1.0-SNAPSHOT.jar");
    System.out.println("\nEnjoy! :-)");
  }
}
