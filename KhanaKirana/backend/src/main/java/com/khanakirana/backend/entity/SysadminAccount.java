package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.khanakirana.backend.utils.AccountType;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class SysadminAccount extends AbstractUser {

    public SysadminAccount() {
    }

    public SysadminAccount(String name, String address, String email, String mobile, String password, String city, String state, Double latitude, Double longitude, Boolean googleUser) {

        super(name, address, city, state, email, mobile, password, latitude, longitude, googleUser, AccountType.SYSADMIN, Boolean.TRUE);
    }
}