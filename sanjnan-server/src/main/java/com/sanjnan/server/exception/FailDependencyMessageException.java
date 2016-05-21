/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

import java.io.IOException;

/**
 * Created by subrat on 15/3/16.
 */
public class FailDependencyMessageException extends HubbleBaseException {
    /**
     * Instantiates a new Fail dependency message exception.
     *
     * @throws IOException the io exception
     */
    public FailDependencyMessageException(String message) {
      super(message);
    }
  public FailDependencyMessageException(String message, Throwable throwable) {
    super(message, throwable);
  }
}

