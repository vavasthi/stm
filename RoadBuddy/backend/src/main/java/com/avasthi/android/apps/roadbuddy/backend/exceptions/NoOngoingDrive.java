package com.avasthi.android.apps.roadbuddy.backend.exceptions;

/**
 * Created by vavasthi on 22/11/15.
 */
public class NoOngoingDrive extends DriveException {

    public NoOngoingDrive(String email) {
        super(email, "Member is not on a drive.");
    }
    private String email;
}