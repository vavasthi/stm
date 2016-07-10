package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class NoDisaggregationPolicyException extends ServiceException {

    public NoDisaggregationPolicyException(Long masterItemId) {
        super(404, String.format("No disaggregation policy for %d.", masterItemId));
    }
    public NoDisaggregationPolicyException(String message) {
        super(404, message);
    }
}
