package com.avasthi.android.apps.roadbuddy.backend.bean;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by vavasthi on 22/3/15.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class RoadMeasurementBean {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    Date timestamp;
    @Persistent
    double accuracy;
    @Persistent
    double altitude;
    @Persistent
    float bearing;
    @Persistent
    double latitude;
    @Persistent
    double longitude;
    @Persistent
    float speed;
    @Persistent
    float accelX;
    @Persistent
    float accelY;
    @Persistent
    float accelZ;
    @Persistent
    float accelXDev;
    @Persistent
    float accelYDev;
    @Persistent
    float accelZDev;

    public RoadMeasurementBean() {

    }
    public RoadMeasurementBean(Long key,
                               Date timestamp,
                               double accuracy,
                               double altitude,
                               float bearing,
                               double latitude,
                               double longitude,
                               float speed,
                               float accelX,
                               float accelY,
                               float accelZ) {
        this.id = key;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.bearing = bearing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.accelX = accelX;
        this.accelY = accelY;
        this.accelZ = accelZ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAccelX() {
        return accelX;
    }

    public void setAccelX(float accelX) {
        this.accelX = accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public void setAccelY(float accelY) {
        this.accelY = accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }

    public void setAccelZ(float accelZ) {
        this.accelZ = accelZ;
    }

    public float getAccelXDev() {
        return accelXDev;
    }

    public void setAccelXDev(float accelXDev) {
        this.accelXDev = accelXDev;
    }

    public float getAccelYDev() {
        return accelYDev;
    }

    public void setAccelYDev(float accelYDev) {
        this.accelYDev = accelYDev;
    }

    public float getAccelZDev() {
        return accelZDev;
    }

    public void setAccelZDev(float accelZDev) {
        this.accelZDev = accelZDev;
    }
}
