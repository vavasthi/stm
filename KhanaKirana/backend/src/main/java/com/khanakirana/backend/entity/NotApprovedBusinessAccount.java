package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.khanakirana.backend.utils.AccountType;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class NotApprovedBusinessAccount extends AbstractUser {

    public NotApprovedBusinessAccount() {
    }
    public NotApprovedBusinessAccount(String name, String address, String email, String mobile, String password, String city, String state, Double latitude, Double longitude, Boolean googleUser) {

        super(name, address, city, state, email, mobile, password, latitude, longitude, googleUser, AccountType.BUSINESS, Boolean.TRUE);
    }
    public NotApprovedBusinessAccount(BusinessAccount account) {

        super(account.getName(), account.getAddress(), account.getRegion().getCity(), account.getRegion().getState(), account.getEmail(), account.getMobile(), account.getPassword(), account.getLatitude(), account.getLongitude(), account.getGoogleUser(), AccountType.createFromValues(account.getAccountType()), account.getLocked());
    }
}