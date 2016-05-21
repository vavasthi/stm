/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * Created by mmaji on 20/1/16.
 */
public class BusinessInterfaceException extends HubbleBaseException {

    /**
     * Instantiates a new Business interface exception.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param throwable the throwable
     */
    public BusinessInterfaceException(int errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }


}
