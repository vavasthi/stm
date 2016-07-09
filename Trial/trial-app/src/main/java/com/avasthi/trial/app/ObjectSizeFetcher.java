package com.avasthi.trial.app;

import java.lang.instrument.Instrumentation;

/**
 * Created by vavasthi on 18/6/16.
 */
public class ObjectSizeFetcher {
  private static Instrumentation instrumentation;

  public static void premain(String args, Instrumentation inst) {
    instrumentation = inst;
  }

  public static long getObjectSize(Object o) {
    return instrumentation.getObjectSize(o);
  }
}
