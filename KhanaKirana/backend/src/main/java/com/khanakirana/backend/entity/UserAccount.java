package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.khanakirana.backend.utils.AccountType;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class UserAccount extends AbstractUser {

    public UserAccount() {
    }

    public UserAccount(String name, String address, String email, String mobile, String city, String state, Double latitude, Double longitude) {

        super(name, address, city, state, email, mobile, latitude, longitude, Boolean.TRUE, AccountType.CUSTOMER, Boolean.FALSE);
    }

}