package com.sanjnan.server.policies.strings;

/**
 * Created by vinay on 2/24/16.
 */
public enum Qualifier {

  BEGINS("begins"),
  ENDS("ends"),
  EXACTLY("exactly"),
  ATLEAST("atleast"),
  ATMOST("atmost");

  private final String value;

  Qualifier(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  public static Qualifier createFromString(String value) {
    for (Qualifier q : values()) {
      if (q.value.equalsIgnoreCase(value)) {
        return q;
      }
    }
    throw new IllegalArgumentException(String.format("%s is not a valid qualifier", value));
  }
}
