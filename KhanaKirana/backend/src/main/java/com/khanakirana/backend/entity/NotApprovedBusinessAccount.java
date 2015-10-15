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
    public NotApprovedBusinessAccount(BusinessAccount account) {
        super(account.getName(), account.getAddress(), account.getRegion().getCity(), account.getRegion().getState(), account.getEmail(), account.getMobile(), account.getLatitude(), account.getLongitude(), true, AccountType.createFromValues(account.getAccountType()), account.getLocked());
    }
}