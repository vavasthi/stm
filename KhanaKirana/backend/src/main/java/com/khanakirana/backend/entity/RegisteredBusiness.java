package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class RegisteredBusiness {
    public RegisteredBusiness() {
    }

    @Id
    private Long id;
    @Index
    private String email;
    @Index
    private String businessName;
    @Index
    private String address1;
    @Index
    private String address2;
    @Index
    private UserAccountRegion region;
    private Double latitude;
    private Double longitude;
}