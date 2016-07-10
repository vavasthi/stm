package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class BusinessAdminAccount extends AbstractAccount {

    public BusinessAdminAccount() {
    }
    public BusinessAdminAccount(String name,
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
    }
}