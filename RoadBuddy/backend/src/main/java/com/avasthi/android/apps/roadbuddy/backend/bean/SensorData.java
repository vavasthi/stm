package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class SensorData extends AbstractEntity {
    public SensorData() {
    }

    public SensorData(Long userId,
                      Long driveId,
                      Date timestamp,
                      Float verticalAccelerometerMean,
                      Float verticalAccelerometerSD,
                      Double latitude,
                      Double longitude,
                      Float accuracy,
                      Float bearing,
                      Float speed) {
        this.userId = userId;
        this.driveId = driveId;
        this.accuracy = accuracy;
        this.bearing = bearing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.timestamp = timestamp;
        this.verticalAccelerometerMean = verticalAccelerometerMean;
        this.verticalAccelerometerSD = verticalAccelerometerSD;
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

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getBearing() {
        return bearing;
    }

    public void setBearing(Float bearing) {
        this.bearing = bearing;
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

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getVerticalAccelerometerMean() {
        return verticalAccelerometerMean;
    }

    public void setVerticalAccelerometerMean(Float verticalAccelerometerMean) {
        this.verticalAccelerometerMean = verticalAccelerometerMean;
    }

    public Float getVerticalAccelerometerSD() {
        return verticalAccelerometerSD;
    }

    public void setVerticalAccelerometerSD(Float verticalAccelerometerSD) {
        this.verticalAccelerometerSD = verticalAccelerometerSD;
    }

    @Index
    private Long userId;
    @Index
    private Long driveId;
    @Index
    private Date timestamp;
    private Float verticalAccelerometerMean;
    private Float verticalAccelerometerSD;
    @Index
    private Double latitude;
    @Index
    private Double longitude;
    private Float speed;
    private Float accuracy;
    private Float bearing;
}
