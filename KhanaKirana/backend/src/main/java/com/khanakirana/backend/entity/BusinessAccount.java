package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.khanakirana.backend.utils.AccountType;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAccount extends AbstractUser {

    public BusinessAccount() {
    }
    public BusinessAccount(String name, String address, String email, String mobile, String city, String state, Double latitude, Double longitude) {

        super(name, address, city, state, email, mobile, latitude, longitude, Boolean.TRUE, AccountType.BUSINESS, Boolean.TRUE);
    }

}