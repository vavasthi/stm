/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * Created by vinay on 1/11/16.
 */
public class HubbleBaseException extends RuntimeException {

  private int errorCode;

  /**
   * Instantiates a new Hubble base exception.
   */
  public HubbleBaseException() {
    super("");
  }

  /**
   * Instantiates a new Hubble base exception.
   *
   * @param errorCode the error code
   * @param message   the message
   */
  public HubbleBaseException(int errorCode,String message) {
    super(message);
    this.errorCode=errorCode;
  }

  /**
   * Instantiates a new Hubble base exception.
   *
   * @param message the message
   */
  public HubbleBaseException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Hubble base exception.
   *
   * @param cause the cause
   */
  public HubbleBaseException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Hubble base exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public HubbleBaseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Hubble base exception.
   *
   * @param message            the message
   * @param cause              the cause
   * @param enableSuppression  the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public HubbleBaseException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Gets error code.
   *
   * @return the error code
   */
  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

}
