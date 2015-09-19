package com.khanakirana.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAdministrator {
    public BusinessAdministrator() {
    }

    public BusinessAdministrator(Long id, RegisteredBusiness registeredBusiness) {
        this.id = id;
        this.registeredBusiness = registeredBusiness;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisteredBusiness getRegisteredBusiness() {
        return registeredBusiness;
    }

    public void setRegisteredBusiness(RegisteredBusiness registeredBusiness) {
        this.registeredBusiness = registeredBusiness;
    }

    @Override
    public String toString() {
        return "BusinessAdministrator{" +
                "id=" + id +
                ", registeredBusiness=" + registeredBusiness +
                '}';
    }

    @Id
    private Long id;
    @Index
    private RegisteredBusiness registeredBusiness;
}