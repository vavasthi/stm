package com.khanakirana.backend.exceptions;

import com.google.api.server.spi.ServiceException;
import com.khanakirana.backend.MeasurementCategory;

/**
 * Created by vavasthi on 13/9/15.
 */
public class MeasurementPrimaryUnitException extends ServiceException {


    private MeasurementPrimaryUnitException(MeasurementCategory measurementCategory, String message) {
        super(404, measurementCategory.toString() + message);
    }
    public static MeasurementPrimaryUnitException getExceptionForPrimaryUnitAlreadyExists(MeasurementCategory measurementCategory) {
        return new MeasurementPrimaryUnitException(measurementCategory, "Measurement category primary unit already exists.");
    }
    public static MeasurementPrimaryUnitException getExceptionForPrimaryUnitDoesntExist(MeasurementCategory measurementCategory) {
        return new MeasurementPrimaryUnitException(measurementCategory, "Measurement category primary unit doesn't exist.");
    }
}
