package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class Drive extends AbstractEntity {
    public Drive() {
        super();
    }

    public Drive(Long eventId, Long groupId, Long memberId, Double latitude, Double longitude) {
        super();
        this.eventId = eventId;
        this.groupId = groupId;
        this.memberId = memberId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastLatitude = latitude;
        this.lastLongitude = longitude;
        distanceCovered = 0.0;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
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

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Double getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(Double distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    @Index
    private Long groupId;
    @Index
    private Long eventId;
    @Index
    private Long memberId;
    @Index
    private Date completedAt;
    @Index
    private Boolean done = Boolean.FALSE;
    private Double latitude;
    private Double longitude;
    private Double lastLatitude;
    private Double lastLongitude;
    private Double distanceCovered;
}
