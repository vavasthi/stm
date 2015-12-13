package com.avasthi.roadcompanion.utils;

import java.util.Date;

/**
 * Created by vavasthi on 13/12/15.
 */
public class RCSensorData {

    public RCSensorData(Float accuracy, Float bearing, Double latitude, Double longitude, Float speed, Float verticalAccelerometer) {
        timestamp = new Date();
        this.accuracy = accuracy;
        this.bearing = bearing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.verticalAccelerometer = verticalAccelerometer;
        valid = (accuracy != null && bearing != null && latitude != null && longitude != null && speed != null && verticalAccelerometer != null);
    }

    public RCSensorData() {
        valid = false;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
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

    public Float getVerticalAccelerometer() {
        return verticalAccelerometer;
    }

    public void setVerticalAccelerometer(Float verticalAccelerometer) {
        this.verticalAccelerometer = verticalAccelerometer;
    }

    private Date timestamp;
    private Float verticalAccelerometer;
    private Double latitude;
    private Double longitude;
    private Float speed;
    private Float accuracy;
    private Float bearing;

    private Boolean valid;
}
