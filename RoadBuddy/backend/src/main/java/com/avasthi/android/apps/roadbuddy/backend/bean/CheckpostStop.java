package com.avasthi.android.apps.roadbuddy.backend.bean;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
public class CheckpostStop extends AbstractStop {
    public CheckpostStop() {

    }
    public CheckpostStop(Long userId, Long establishmentId, Date timestamp, Boolean speedCameras) {
        super(userId, establishmentId, timestamp);
        this.speedCameras = speedCameras;
    }

    public Boolean getSpeedCameras() {
        return speedCameras;
    }

    public void setSpeedCameras(Boolean speedCameras) {
        this.speedCameras = speedCameras;
    }

    private Boolean speedCameras;
}
