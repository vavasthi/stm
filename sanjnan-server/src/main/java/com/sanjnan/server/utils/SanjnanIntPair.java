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
public class SanjnanIntPair implements Serializable {

  public SanjnanIntPair(int first, int second) {
    this.first = first;
    this.second = second;
  }

  public SanjnanIntPair() {
  }

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public int getSecond() {
    return second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SanjnanIntPair that = (SanjnanIntPair) o;

    if (first != that.first) return false;
    return second == that.second;

  }

  @Override
  public int hashCode() {
    int result = first;
    result = 31 * result + second;
    return result;
  }

  private int first;
  private int second;
}
