package com.inigo.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassLogger implements ClassFileTransformer {
  static List<String> transformed = new ArrayList<>();
  ClassPool cp;
  String packageToLog;
  public ClassLogger(ClassPool cp, String packageToLog) {
    this.cp = cp;
    this.packageToLog = packageToLog;
  }

  @Override
  public byte[] transform(ClassLoader loader,
                          String className,
                          Class<?> classBeingRedefined,
                          ProtectionDomain protectionDomain,
                          byte[] classfileBuffer) throws IllegalClassFormatException {
    addIfAbsent(cp);
    if (className.startsWith("com/inigo/agent")) {
      return null;
    }
    if (!className.startsWith(packageToLog)
            || transformed.contains(className)
            || className.contains("Main")) {
      return classfileBuffer;
    }
    transformed.add(className);
    return transform(className, cp, classfileBuffer);
  }

  private byte[] transform(String className, ClassPool cp, byte[] classfileBuffer){
    try {
      CtClass ct = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
      ct.defrost();
      CtMethod[] declaredMethods = ct.getDeclaredMethods();
      for (CtMethod method : declaredMethods) {
        method.insertAfter("{ Stack.pop(); }", true);
        method.insertBefore("{ Stack.push();Stack.log(\"" + className + "." + method.getName() + "\"); }");
      }
      return ct.toBytecode();
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return classfileBuffer;
  }

  private void addIfAbsent(ClassPool cp) {
    boolean esta = false;
    Iterator iterator = cp.getImportedPackages();
    while (iterator.hasNext()) {
      if (iterator.next().equals("com.inigo.agent")) {
        esta = true;
      }
    }
    if (!esta){
      cp.importPackage("com.inigo.agent");
    }
  }
}