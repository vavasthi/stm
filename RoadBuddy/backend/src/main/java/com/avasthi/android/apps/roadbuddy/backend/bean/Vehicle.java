package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class Vehicle extends AbstractEntity {

    public Vehicle() {
        super();
    }

    public Vehicle(Long ownerId, String brand, String registration) {
        this.ownerId = ownerId;
        Brand = brand;
        this.registration = registration;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    private String Brand;
    @Index
    private String registration;
    @Index
    private Long ownerId;
}
