package com.avasthi.roadcompanion.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class MeasurementCategoryDoesntExist extends ServiceException {

    public MeasurementCategoryDoesntExist() {
        super(404, "Measurement Category Doesnt Exist.");
    }
}
