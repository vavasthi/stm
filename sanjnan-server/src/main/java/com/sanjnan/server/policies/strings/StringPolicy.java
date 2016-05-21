package com.sanjnan.server.policies.strings;

/**
 * Created by vinay on 2/24/16.
 */
public class StringPolicy {
  public StringPolicy() {

  }

  public StringPolicy(Qualifier qualifier, int count, StringPolicyComponent[] policies) {
    this.qualifier = qualifier;
    this.count = count;
    this.policies = policies;
  }

  public Qualifier getQualifier() {
    return qualifier;
  }

  public void setQualifier(Qualifier qualifier) {
    this.qualifier = qualifier;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public StringPolicyComponent[] getPolicies() {
    return policies;
  }

  public void setPolicies(StringPolicyComponent[] policies) {
    this.policies = policies;
  }
  public boolean evaluate(String string) {
    int matchCount = 0;
    for (int i = 0; i < policies.length; ++i) {
      if (policies[i].evaluate(string)) {
        ++matchCount;
      }
    }
    if(qualifier.equals(Qualifier.ATLEAST) && matchCount >= count) {
      return true;
    }
    else if (qualifier.equals(Qualifier.ATMOST) && matchCount <= count) {
      return true;
    }
    else if(qualifier.equals(Qualifier.EXACTLY) && matchCount == count) {
      return true;
    }
    return false;
  }
  private Qualifier qualifier;
  private int count;
  private StringPolicyComponent[] policies;
}
