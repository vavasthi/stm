/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

/**
 * Created by maheshsapre on 16/03/16.
 */
public class InfrastructurePlatformException extends HubbleBaseException {
    /**
     * Instantiates a new Infrastructure platform exception.
     *
     * @param messgae   the messgae
     * @param throwable the throwable
     */
    public InfrastructurePlatformException(String messgae, Throwable throwable) {
        super(messgae, throwable);
    }
}
