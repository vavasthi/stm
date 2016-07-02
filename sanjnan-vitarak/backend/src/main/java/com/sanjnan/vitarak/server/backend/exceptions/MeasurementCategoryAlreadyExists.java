package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class MeasurementCategoryAlreadyExists extends ServiceException {

    public MeasurementCategoryAlreadyExists() {
        super(404, "Measurement Category Already Exists.");
    }
}
