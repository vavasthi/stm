package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vavasthi on 10/7/16.
 */
@Entity
public class DeliveryTripEntity implements Serializable {
    public DeliveryTripEntity(Long businessId, Long userId, String name, Date timestamp) {
        this.businessId = businessId;
        this.userId = userId;
        this.name = name;
        this.timestamp = timestamp;
        this.done = false;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Id
    private Long id;
    @Index
    private Long businessId;
    @Index
    private Long userId;
    private String name;
    private Date timestamp;
    private Boolean done;
}
