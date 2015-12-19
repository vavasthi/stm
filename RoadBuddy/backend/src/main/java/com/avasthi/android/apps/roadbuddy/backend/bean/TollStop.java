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
    public TollStop(Long userId, Long driveId, Long establishmentId, Date timestamp, Float amount) {
        super(userId, driveId, establishmentId, timestamp);
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
