package com.avasthi.android.apps.roadbuddy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Service;
import android.util.Log;

import com.appspot.myapplicationid.roadMeasurementBeanApi.model.RoadMeasurementBean;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.api.client.util.DateTime;

public class SensorInterface implements SensorEventListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LocationManager locationManager_;
    static private SensorInterface self = null;

    static final private String NO_GRAVITY_MEASURE = "NO_GRAVITY_MEASURE";
    static final private String NO_GPS_MEASURE = "NO_GPS_MEASURE";
    private RoadConditionFullscreenActivity mainActivity;

    private long lastProcessingSecond;
    private Date lastProcessingDate;

    private List<GPSMeasure> gpsMeasureList = new ArrayList<>();
    private List<GravityMeasure> gravityMeasureList = new ArrayList<>();
    private boolean dataCollection;


    private SensorInterface(RoadConditionFullscreenActivity mainActivity) {

        this.mainActivity = mainActivity;
        this.dataCollection = false;
        lastProcessingSecond = 0;
        // Get the reference to the sensor manager
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) mainActivity.getSystemService(Service.SENSOR_SERVICE);
        // Get the list of sensor
        registerSensorListeners(sensorManager);
    }

    public static void init(RoadConditionFullscreenActivity mainActivity) {
        if (self == null) {
            synchronized(SensorInterface.class) {
                if (self == null) {
                    self = new SensorInterface(mainActivity);
                }
            }
        }
    }
    public static SensorInterface getInstance() {
        return self;
    }

    void registerSensorListeners(android.hardware.SensorManager sm) {
        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            if (s.getType() == Sensor.TYPE_GRAVITY) {

                sm.registerListener(this, s, 5000);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        try {
            if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                addGravityMeasure(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(StatisticsException e) {
            e.printStackTrace();
        }

/*		ListView lv = (ListView) findViewById(R.id.listView1);
		SensorDetailAdapter sda = (SensorDetailAdapter)lv.getAdapter();
		sda.setValues(event.sensor, event.values);
		sda.setLocation(locationManager_.getLastKnownLocation(LOCATION_SERVICE));*/
    }
    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        Log.v(Activity.LOCATION_SERVICE, "Location change receieved " + location.getLatitude() + "," + location.getLongitude());
        try {
            addGPSMeasure(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*		ListView lv = (ListView) findViewById(R.id.listView1);
		SensorDetailAdapter sda = (SensorDetailAdapter)lv.getAdapter();
		sda.setLocation(location);*/
    }
    private void addGPSMeasure(Location location) throws IOException {

        gpsMeasureList.add(new GPSMeasure(location));
    }
    private void addGravityMeasure(SensorEvent event) throws IOException, StatisticsException {

        long eventSecond = event.timestamp / 1000000000;
        if (eventSecond > lastProcessingSecond) {

            processMeasures();
            lastProcessingSecond = eventSecond;
        }
        gravityMeasureList.add(new GravityMeasure(event.values));
    }
    private void processMeasures() throws IOException, StatisticsException {

        if (dataCollection) {

            lastProcessingDate = new Date();
            lastProcessingDate.setTime((lastProcessingDate.getTime() / 1000)*1000);

            double[] accuracy = new double[gpsMeasureList.size()];
            double[] altitude = new double[gpsMeasureList.size()];
            float[] bearing = new float[gpsMeasureList.size()];
            double[] latitude = new double[gpsMeasureList.size()];
            double[] longitude = new double[gpsMeasureList.size()];
            float[] speed = new float[gpsMeasureList.size()];
            float[] accelX = new float[gravityMeasureList.size()];
            float[] accelY = new float[gravityMeasureList.size()];
            float[] accelZ = new float[gravityMeasureList.size()];

            double accuracyValue = 0;
            double altitudeValue = 0;
            float bearingValue = 0;
            double latitudeValue = 0;
            double longitudeValue = 0;
            float speedValue = 0;
            float accelXValue = 0;
            float accelYValue = 0;
            float accelZValue = 0;
            float accelXDev = 0;
            float accelYDev = 0;
            float accelZDev = 0;
            int i = 0;
            if (gpsMeasureList.size() > 0) {

                Log.e(NO_GPS_MEASURE, "GPS Measure List contains " + gravityMeasureList.size() + " elements");
                for (GPSMeasure m : gpsMeasureList) {
                    accuracy[i] = m.accuracy;
                    altitude[i] = m.altitude;
                    bearing[i] = m.bearing;
                    latitude[i] = m.latitude;
                    longitude[i] = m.longitude;
                    speed[i] = m.speed;
                    ++i;
                }
                accuracyValue = StatisticsPack.mean(accuracy);
                altitudeValue = StatisticsPack.mean(altitude);
                bearingValue = StatisticsPack.mean(bearing);
                latitudeValue = StatisticsPack.median(latitude);
                longitudeValue = StatisticsPack.median(longitude);
                speedValue = StatisticsPack.median(speed);
            }
            else {

                Log.e(NO_GPS_MEASURE, "GPS Measure List contains no elements");
            }
            if (gravityMeasureList.size() > 0) {

                Log.e(NO_GRAVITY_MEASURE, "Gravity Measure List contains " + gravityMeasureList.size() + " elements");
                i = 0;
                for (GravityMeasure m : gravityMeasureList) {
                    accelX[i] = m.x;
                    accelY[i] = m.y;
                    accelZ[i] = m.z;
                    ++i;
                }
                accelXValue = StatisticsPack.mean(accelX);
                accelYValue = StatisticsPack.mean(accelY);
                accelZValue = StatisticsPack.mean(accelZ);
                accelXDev = StatisticsPack.stddev(accelX);
                accelYDev = StatisticsPack.stddev(accelY);
                accelZDev = StatisticsPack.stddev(accelZ);
                Log.i("VARIANCE VALUES", "Values are " + accelXDev + " " + accelYDev + " " + accelZDev);
            }
            else {
                Log.e(NO_GPS_MEASURE, "Gravity Measure List has zero elements.");
            }

            if (gpsMeasureList.size() != 0 && gravityMeasureList.size() != 0) {

                mainActivity.getDbHandler().addMeasurement(getMeasurementEntity(accuracyValue,
                        altitudeValue,
                        bearingValue,
                        latitudeValue,
                        longitudeValue,
                        speedValue,
                        accelXValue,
                        accelYValue,
                        accelZValue,
                        accelXDev,
                        accelYDev,
                        accelZDev));
                gravityMeasureList.clear();
                gpsMeasureList.clear();
            }
        }
    }

    private RoadMeasurementBean getMeasurementEntity(double accuracy,
                                                     double altitude,
                                                     float bearing,
                                                     double latitude,
                                                     double longitude,
                                                     float speed,
                                                     float accelX,
                                                     float accelY,
                                                     float accelZ,
                                                     float accelXDev,
                                                     float accelYDev,
                                                     float accelZDev) {
        RoadMeasurementBean bean = new RoadMeasurementBean();
        bean.setTimestamp(new DateTime(lastProcessingDate));
        bean.setAccuracy(accuracy);
        bean.setAltitude(altitude);
        bean.setBearing(bearing);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setSpeed(speed);
        bean.setAccelX(accelX);
        bean.setAccelY(accelY);
        bean.setAccelZ(accelZ);
        bean.setAccelXDev(accelXDev);
        bean.setAccelYDev(accelYDev);
        bean.setAccelZDev(accelZDev);
        return bean;
    }

    Map<Date, CompositeMeasure> compositeMeasureMap = new HashMap<>();

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mainActivity.getApiClient(), locationRequest, this);
        SensorInterface.getInstance().startDataCollection();
    }

    private void startDataCollection() {
        dataCollection = true;
    }

    @Override
    public void onConnectionSuspended(int i) {

        SensorInterface.getInstance().stopDataCollection();
    }
    private void stopDataCollection() {
        dataCollection = false;
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("LOCATION_UNAVAILABLE","Location Listener Failed.");
    }
}
