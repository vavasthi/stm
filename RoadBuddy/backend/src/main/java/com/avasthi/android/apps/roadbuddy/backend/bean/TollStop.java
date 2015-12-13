package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class TollStop extends AbstractStop {
    public TollStop() {

    }
    public TollStop(Long userId, Long establishmentId, Date timestamp, Float amount, Boolean fastTagLane) {
        super(userId, establishmentId, timestamp);
        this.amount = amount;
        this.fastTagLane = fastTagLane;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getFastTagLane() {
        return fastTagLane;
    }

    public void setFastTagLane(Boolean fastTagLane) {
        this.fastTagLane = fastTagLane;
    }

    private Float amount;
    private Boolean fastTagLane;
}
