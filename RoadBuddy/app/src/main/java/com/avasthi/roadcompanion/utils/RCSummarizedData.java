package com.avasthi.roadcompanion.utils;

import java.util.Date;

/**
 * Created by vavasthi on 13/12/15.
 */
public class RCSummarizedData {
    public RCSummarizedData(Long id, Date timestamp, Float accuracy, Float bearing, Double latitude, Double longitude, Float speed, Float verticalAccelerometerMean, Float verticalAccelerometerSD) {
        this.id = id;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.bearing = bearing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.verticalAccelerometerMean = verticalAccelerometerMean;
        this.verticalAccelerometerSD = verticalAccelerometerSD;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    long id;
    Date timestamp;
    private Float verticalAccelerometerSD;
    private Float verticalAccelerometerMean;
    private Double latitude;
    private Double longitude;
    private Float speed;
    private Float accuracy;
    private Float bearing;
}
