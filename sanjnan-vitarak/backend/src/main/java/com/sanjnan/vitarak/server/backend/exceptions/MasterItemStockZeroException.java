package com.sanjnan.vitarak.server.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 13/9/15.
 */
public class MasterItemStockZeroException extends ServiceException {

    public MasterItemStockZeroException(Long masterItemId) {
        super(404, String.format("Stock for Master Item %d is zero.", masterItemId));
    }
    public MasterItemStockZeroException(String message) {
        super(404, message);
    }
}
