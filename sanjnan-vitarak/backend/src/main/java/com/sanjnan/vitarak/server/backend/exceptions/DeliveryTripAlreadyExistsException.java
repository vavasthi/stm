package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.client.http.HttpStatusCodes;
import com.google.api.server.spi.ServiceException;

import org.apache.http.HttpStatus;

/**
 * Created by vavasthi on 13/9/15.
 */
public class DeliveryTripAlreadyExistsException extends ServiceException {


    public DeliveryTripAlreadyExistsException(String message) {
        super(HttpStatus.SC_CONFLICT, message);
    }
}
