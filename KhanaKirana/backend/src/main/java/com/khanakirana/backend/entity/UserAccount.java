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

    public UserAccount(String name, String address, String email, String mobile, String password, String city, String state, Double latitude, Double longitude,Boolean googleUser) {

        super(name, address, city, state, email, mobile, password, latitude, longitude, googleUser, AccountType.CUSTOMER, Boolean.TRUE);
    }

}