package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

import org.apache.http.HttpStatus;

/**
 * Created by vavasthi on 13/9/15.
 */
public class DeliveryUserDoesntExistException extends ServiceException {


    public DeliveryUserDoesntExistException(String message) {
        super(HttpStatus.SC_NOT_FOUND, message);
    }
}
