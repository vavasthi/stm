package com.avasthi.roadcompanion;


import android.location.Location;

/**
 * Created by vavasthi on 15/3/15.
 */
public class GPSMeasure {
    GPSMeasure(Location location) {
        this.accuracy = location.getAccuracy();
        this.altitude = location.getAltitude();
        this.bearing = location.getBearing();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.speed = location.getSpeed();
    }
    double accuracy;
    double altitude;
    float bearing;
    double latitude;
    double longitude;
    float speed;
}
