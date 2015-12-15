package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class Toll extends Establishment {
    public Toll() {

    }
    public Toll(Long userId, Date timestamp, Boolean fasTagLane, Double latitude, Double longitude, Float amount, String city, String state, String country) {
        super(userId, timestamp, latitude, longitude, city, state, country);
        this.amount = amount;
        this.fasTagLane = fasTagLane;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getFasTagLane() {
        return fasTagLane;
    }

    public void setFasTagLane(Boolean fasTagLane) {
        this.fasTagLane = fasTagLane;
    }

    private Boolean fasTagLane;
    private Float amount;

}
