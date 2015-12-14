package com.avasthi.android.apps.roadbuddy.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 22/11/15.
 */
public class DriveException extends ServiceException {

    public DriveException(String email, String message) {
        super(404, message);
        this.email = email;
    }
    private String email;
}