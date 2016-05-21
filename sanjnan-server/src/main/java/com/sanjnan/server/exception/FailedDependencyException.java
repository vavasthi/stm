/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * The type Failed dependency exception.
 *
 * @author nikhilvs
 */
public class FailedDependencyException extends HubbleBaseException {

    /**
     * Instantiates a new Failed dependency exception.
     *
     * @param message the message
     */
    public FailedDependencyException(String message) {
    super(message);
  }

    /**
     * Instantiates a new Failed dependency exception.
     *
     * @param message the message
     * @param code    the code
     */
    public FailedDependencyException(String message, Integer code) {
    super(code,message);
 }

}
