/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * Created by shrikanth on 25/2/16.
 */
public class InternalErrorWithDataException  extends HubbleBaseException {
    /**
     * The Response.
     */
    String response;

    /**
     * Instantiates a new Internal error with data exception.
     *
     * @param message the message
     */
    public InternalErrorWithDataException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Internal error with data exception.
     *
     * @param message  the message
     * @param code     the code
     * @param response the response
     */
    public InternalErrorWithDataException(String message, Integer code,String response) {
        super(code,message);
        this.response =response;
    }

    /**
     * Gets response.
     *
     * @return the response
     */
    public String getResponse() {
        return response;
    }


}
