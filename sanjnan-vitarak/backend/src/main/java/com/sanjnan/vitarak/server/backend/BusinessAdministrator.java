package com.sanjnan.vitarak.server.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.sanjnan.vitarak.server.backend.entity.BusinessEstablishmentAccount;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAdministrator {
    public BusinessAdministrator() {
    }

    public BusinessAdministrator(Long id, BusinessEstablishmentAccount registeredBusinessEstablishmentAccount) {
        this.id = id;
        this.registeredBusinessEstablishmentAccount = registeredBusinessEstablishmentAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessEstablishmentAccount getRegisteredBusinessEstablishmentAccount() {
        return registeredBusinessEstablishmentAccount;
    }

    public void setRegisteredBusinessEstablishmentAccount(BusinessEstablishmentAccount registeredBusinessEstablishmentAccount) {
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
    private BusinessEstablishmentAccount registeredBusinessEstablishmentAccount;
}