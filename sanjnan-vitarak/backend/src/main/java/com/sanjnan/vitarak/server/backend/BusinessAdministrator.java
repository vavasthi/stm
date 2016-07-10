package com.sanjnan.vitarak.server.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.sanjnan.vitarak.server.backend.entity.BusinessAdminAccount;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAdministrator {
    public BusinessAdministrator() {
    }

    public BusinessAdministrator(Long id, BusinessAdminAccount registeredBusinessEstablishmentAccount) {
        this.id = id;
        this.registeredBusinessEstablishmentAccount = registeredBusinessEstablishmentAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessAdminAccount getRegisteredBusinessEstablishmentAccount() {
        return registeredBusinessEstablishmentAccount;
    }

    public void setRegisteredBusinessEstablishmentAccount(BusinessAdminAccount registeredBusinessEstablishmentAccount) {
        this.registeredBusinessEstablishmentAccount = registeredBusinessEstablishmentAccount;
    }

    @Override
    public String toString() {
        return "BusinessAdministrator{" +
                "id=" + id +
                ", registeredBusinessEstablishmentAccount=" + registeredBusinessEstablishmentAccount +
                '}';
    }

    @Id
    private Long id;
    @Index
    private BusinessAdminAccount registeredBusinessEstablishmentAccount;
}