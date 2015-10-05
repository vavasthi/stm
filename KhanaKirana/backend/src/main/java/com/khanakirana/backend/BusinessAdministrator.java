package com.khanakirana.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.khanakirana.backend.entity.BusinessAccount;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAdministrator {
    public BusinessAdministrator() {
    }

    public BusinessAdministrator(Long id, BusinessAccount registeredBusinessAccount) {
        this.id = id;
        this.registeredBusinessAccount = registeredBusinessAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessAccount getRegisteredBusinessAccount() {
        return registeredBusinessAccount;
    }

    public void setRegisteredBusinessAccount(BusinessAccount registeredBusinessAccount) {
        this.registeredBusinessAccount = registeredBusinessAccount;
    }

    @Override
    public String toString() {
        return "BusinessAdministrator{" +
                "id=" + id +
                ", registeredBusinessAccount=" + registeredBusinessAccount +
                '}';
    }

    @Id
    private Long id;
    @Index
    private BusinessAccount registeredBusinessAccount;
}