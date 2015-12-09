package com.avasthi.roadcompanion.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by vavasthi on 15/10/15.
 */
public class RCSensorManager implements SensorEventListener {

    private static RCSensorManager INSTANCE;
    private SensorManager sensorManager;
    private Context context;

    private float[] lastAccelerometer;
    private float[] lastMagnetic;
    private float[] lastGyro;
    private float[] lastRotationMatrix;
    private float[] lastOrientation;
    private int verticalValue;

    public static synchronized  void initialize(Context context) {
        INSTANCE = new RCSensorManager(context);
        INSTANCE.initializeLocationService();
    }

    public static RCSensorManager getInstance() {
        return INSTANCE;
    }
    private RCSensorManager(Context context) {
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        this.context = context;
    }
    private synchronized void initializeLocationService() {
        int samplingPeriodMicroSeconds = 1000 * 1000 / 10;
        List<Sensor> l = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                samplingPeriodMicroSeconds,
                0);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                samplingPeriodMicroSeconds);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                samplingPeriodMicroSeconds);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                samplingPeriodMicroSeconds);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] v = event.values;
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = v;
                break;
            case Sensor.TYPE_GYROSCOPE:
                lastGyro = v;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastMagnetic = v;
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                lastRotationMatrix = v;
                float[] r = new float[9];
                float[] i = new float[9];
                if (SensorManager.getRotationMatrix(r, i, lastAccelerometer, lastMagnetic)) {

                    v = SensorManager.getOrientation(r, v);
                    SensorManager.getRotationMatrixFromVector(r, event.values);
                    lastOrientation = SensorManager.getOrientation(r, lastOrientation);
                }
                break;
        }
    }

    public void adjustVerticalDirection() {
        if (Math.abs(lastAccelerometer[0]) > Math.max(Math.abs(lastAccelerometer[1]), Math.abs(lastAccelerometer[2]))) {
            verticalValue = 0;
        }
        if (Math.abs(lastAccelerometer[1]) > Math.max(Math.abs(lastAccelerometer[0]), Math.abs(lastAccelerometer[2]))) {
            verticalValue = 1;
        }
        if (Math.abs(lastAccelerometer[2]) > Math.max(Math.abs(lastAccelerometer[0]), Math.abs(lastAccelerometer[1]))) {
            verticalValue = 2;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
