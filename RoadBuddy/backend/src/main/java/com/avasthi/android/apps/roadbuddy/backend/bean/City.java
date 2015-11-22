package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class City {
    public City() {
    }

    public City(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
