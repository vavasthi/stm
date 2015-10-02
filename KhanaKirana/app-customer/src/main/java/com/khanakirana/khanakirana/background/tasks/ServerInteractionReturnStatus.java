package com.khanakirana.khanakirana.background.tasks;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ServerInteractionReturnStatus {
    final static int AUTHENTICATED_BUT_NOT_REGISTERED = 0x01;
    final static int ALREADY_REGISTERED = 0x02;
    final static int AUTHORIZED = 0x03;
    final static int INVALID_USER = 0x04;
    final static int REGISTRATION_FAILED = 0x05;
    final static int SUCCESS = 0x06;
    final static int FATAL_ERROR = 0x99;
}
