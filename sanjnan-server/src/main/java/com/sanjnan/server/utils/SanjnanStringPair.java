/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.utils;

import java.io.Serializable;

/**
 * Created by vavasthi on 23/2/16.
 */
public class SanjnanStringPair implements Serializable {

  public SanjnanStringPair(String first, String second) {
    this.first = first;
    this.second = second;
  }

  public SanjnanStringPair() {
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getSecond() {
    return second;
  }

  public void setSecond(String second) {
    this.second = second;
  }

  @Override
  public String toString() {
    return "SanjnanUUIDPair{" +
        "first=" + first +
        ", second=" + second +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SanjnanStringPair that = (SanjnanStringPair) o;

    if (first != null ? !first.equals(that.first) : that.first != null) return false;
    return second != null ? second.equals(that.second) : that.second == null;

  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    return result;
  }

  private String first;
  private String second;
}
