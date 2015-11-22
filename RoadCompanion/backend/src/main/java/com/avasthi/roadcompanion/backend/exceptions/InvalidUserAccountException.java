package com.avasthi.roadcompanion.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class InvalidUserAccountException extends ServiceException {

    public InvalidUserAccountException() {
        super(404, "Email doesn't correspond to valid user.");
    }
}
