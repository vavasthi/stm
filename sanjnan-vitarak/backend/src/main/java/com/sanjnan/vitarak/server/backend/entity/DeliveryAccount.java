package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class DeliveryAccount extends AbstractAccount {

    public DeliveryAccount() {
    }
    public DeliveryAccount(Long businessId,
                           String name,
                           String address,
                           String email,
                           String mobile,
                           String city,
                           String state,
                           Double latitude,
                           Double longitude) {

        super(name,
                address,
                city,
                state,
                email,
                mobile,
                latitude,
                longitude,
                Boolean.TRUE,
                Boolean.FALSE);
        this.businessId = businessId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Index
    private Long businessId;
}