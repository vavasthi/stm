package com.sanjnan.server.policies.strings;

import java.io.Serializable;

/**
 * Created by vinay on 2/24/16.
 */
public class StringPolicyComponent implements Serializable{

  public StringPolicyComponent(String expression, String qualifier) {

    this.expression = expression;
    this.qualifier = Qualifier.createFromString(qualifier);
    this.count = 1;
  }

  public StringPolicyComponent(String expression, String qualifier, int count) {

    this.expression = expression;
    this.qualifier = Qualifier.createFromString(qualifier);
    this.count = count;
  }
  public StringPolicyComponent() {
    this.count = 0;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
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

  public boolean evaluate(String string) {

    if (qualifier.equals(Qualifier.BEGINS)) {

      String tmp = new Character(string.charAt(0)).toString();
      if (tmp.matches(expression)) {
        return true;
      }
    }
    else if(qualifier.equals(Qualifier.ENDS)) {

      String tmp = new Character(string.charAt(string.length() - 1)).toString();
      if (tmp.matches(expression)) {
        return true;
      }
    }
    int matchCount = 0;
    for (int i = 0; i < string.length(); ++i) {
      String tmp = new Character(string.charAt(i)).toString();
      if (tmp.matches(expression)) {
        ++matchCount;
      }
    }
    if (qualifier.equals(Qualifier.ATLEAST) && matchCount >= count) {
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
  private String expression;
  private Qualifier qualifier;
  private int count;
}
