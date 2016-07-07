package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class InvalidInvoiceException extends ServiceException {

    public InvalidInvoiceException(String invoiceId) {
        super(404, String.format("Invoice id %s doesn't exist.", invoiceId));
    }
}
