package com.avasthi.android.apps.roadbuddy.backend.exceptions;

import com.google.api.server.spi.ServiceException;

/**
 * Created by vavasthi on 22/11/15.
 */
public class AlreadyOngoingDrive extends DriveException {

    public AlreadyOngoingDrive(String email) {
        super(email, "Member is already on a drive.");
    }
    private String email;
}