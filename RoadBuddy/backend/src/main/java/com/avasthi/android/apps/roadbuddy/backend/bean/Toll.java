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
    public Toll(Long userId, Date timestamp, Double latitude, Double longitude, Float amount, String city, String state, String country) {
        super(userId, timestamp, latitude, longitude, city, state, country);
        this.amount = amount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    private Float amount;
}
