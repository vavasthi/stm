package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class CheckpostStop extends AbstractStop {
    public CheckpostStop() {

    }
    public CheckpostStop(Long userId, Long driveId, Long establishmentId, Date timestamp, Boolean speedCameras, Float fineAmount) {
        super(userId, driveId, establishmentId, timestamp);
        this.speedCameras = speedCameras;
        this.fineAmount = fineAmount;
    }

    public Boolean getSpeedCameras() {
        return speedCameras;
    }

    public void setSpeedCameras(Boolean speedCameras) {
        this.speedCameras = speedCameras;
    }

    public Float getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Float fineAmount) {
        this.fineAmount = fineAmount;
    }

    private Boolean speedCameras;
    private Float fineAmount;
}
