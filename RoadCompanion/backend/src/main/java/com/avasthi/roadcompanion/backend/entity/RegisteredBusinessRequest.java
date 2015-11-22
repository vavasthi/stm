package com.avasthi.roadcompanion.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class RegisteredBusinessRequest {
    public RegisteredBusinessRequest() {
    }

    @Id
    private Long id;
    @Index
    private String businessName;
    private String address;
    @Index
    private UserAccountRegion region;
    @Index
    private String email;
    @Index
    private String mobile;
    private Double latitude;
    private Double longitude;
}