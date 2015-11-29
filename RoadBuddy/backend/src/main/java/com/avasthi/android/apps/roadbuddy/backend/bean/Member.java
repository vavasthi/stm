package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class Member {
    public Member() {
    }

    public Member(String name, String email, String mobile, long city, long detectedCity, Double latitude, Double longitude, String facebookUserId) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
        this.detectedCity = detectedCity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facebookUserId = facebookUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDetectedCity() {
        return detectedCity;
    }

    public void setDetectedCity(Long detectedCity) {
        this.detectedCity = detectedCity;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    @Id
    private Long id;
    @Index
    private String name;
    @Index
    private String email;
    @Index
    private String mobile;
    @Index
    private Long city;
    @Index
    private Long detectedCity;
    private Double latitude;
    private Double longitude;
    @Index
    private String facebookUserId;
}
