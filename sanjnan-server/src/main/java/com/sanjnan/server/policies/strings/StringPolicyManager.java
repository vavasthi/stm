package com.sanjnan.server.policies.strings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintStream;

/**
 * Created by vinay on 2/24/16.
 */
public class StringPolicyManager {

  public static StringPolicyManager INSTANCE = new StringPolicyManager();

  private StringPolicy policy = new StringPolicy();

  private StringPolicyManager() {

  }
  public void loadPolicies(String json) {

    Gson gson = new GsonBuilder().create();
    policy = gson.fromJson(json, StringPolicy.class);
  }
  public void print(PrintStream stream) {

    Gson gson = new GsonBuilder().create();
    stream.println(gson.toJson(policy));
  }
  public boolean evaluate(String string) {
    return policy.evaluate(string);
  }
}
