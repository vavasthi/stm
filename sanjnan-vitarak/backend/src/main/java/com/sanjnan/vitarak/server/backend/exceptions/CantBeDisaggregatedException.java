package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class CantBeDisaggregatedException extends ServiceException {

    public CantBeDisaggregatedException(Long masterItemId) {
        super(409, String.format("Item %d can't be disaggregated. It can be sold in fractional quantities.", masterItemId));
    }
    public CantBeDisaggregatedException(String message) {
        super(404, message);
    }
}
