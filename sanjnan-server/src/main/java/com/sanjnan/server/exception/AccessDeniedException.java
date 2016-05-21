/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * Created by mmaji on 10/2/16.
 */
public class AccessDeniedException extends HubbleBaseException{
  /**
   * The Code.
   */
  Integer code;

  /**
   * Instantiates a new Access denied exception.
   *
   * @param message the message
   */
  public AccessDeniedException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Access denied exception.
   *
   * @param message the message
   * @param code    the code
   */
  public AccessDeniedException(String message, Integer code) {
    super(message);
    this.code = code;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public Integer getCode() {
    return code;
  }
}
