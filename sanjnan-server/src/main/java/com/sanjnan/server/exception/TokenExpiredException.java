/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.exception;

import com.sanjnan.server.pojos.constants.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by maheshsapre on 28/01/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends HubbleBaseException {

    /**
     * Instantiates a new Token expired exception.
     *
     * @param errorCode the error code
     * @param Message   the message
     */
    public TokenExpiredException(int errorCode, String Message) {

    super(errorCode, ErrorCodes.TOKEN_EXPIRED_ERROR_CODE + ": " + Message);
  }

}
