package com.khanakirana.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class UserAccountRegion {
    public UserAccountRegion() {
    }

    public UserAccountRegion(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Id
    private Long id;
    @Index
    private String city;
    @Index
    private String state;
}