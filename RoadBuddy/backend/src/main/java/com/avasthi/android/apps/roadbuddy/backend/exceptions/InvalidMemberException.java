package com.avasthi.android.apps.roadbuddy.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 22/11/15.
 */
public class InvalidMemberException extends ServiceException {

    public InvalidMemberException(String email) {
        super(404, "Email doesn't correspond to valid user.");
        this.email = email;
    }
    private String email;
}