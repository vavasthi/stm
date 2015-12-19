package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class AbstractStop extends AbstractEntity {

    public AbstractStop() {

    }
    public AbstractStop(Long userId, Long driveId, Long establishmentId, Date timestamp) {
        this.userId = userId;
        this.driveId = driveId;
        this.establishmentId = establishmentId;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Long establishmentId) {
        this.establishmentId = establishmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    @Index
    private Date timestamp;
    @Index
    private Long userId;
    @Index
    private Long driveId;
    @Index
    private Long establishmentId;
}
