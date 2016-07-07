package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class InvalidMasterItemException extends ServiceException {

    public InvalidMasterItemException(Long masterItemId) {
        super(404, String.format("Invoice id %d doesn't exist.", masterItemId));
    }
}
