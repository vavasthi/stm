package com.sanjnan.vitarak.common;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ServerInteractionReturnStatus {
    public final static int INVALID_USER = 0x01;
    public final static int REGISTRATION_FAILED = 0x02;
    public final static int AUTHENTICATION_FAILED = 0x03;
    public final static int SUCCESS = 0x04;
    public final static int FATAL_ERROR = 0x99;
}
