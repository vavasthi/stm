package com.avasthi.roadcompanion.utils;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Checkpost;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.MemberAndVehicles;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;
import com.avasthi.roadcompanion.service.RCDataCollectorService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vavasthi on 15/10/15.
 */
public class RCLocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public MemberAndVehicles currentMemberAndVehicles;
    private GoogleApiClient googleApiClient;
    private Locale locale;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Address lastAddress;
    private RoadCompanionMainBaseActivity context;
    private PointsOfInterest pointsOfInterest = new PointsOfInterest();

    protected Boolean vehicleMoving = false;


    private static RCLocationManager INSTANCE;

    public static synchronized  void initialize(RoadCompanionMainBaseActivity context, Locale locale) {
        INSTANCE = new RCLocationManager(context, locale);
        INSTANCE.initializeLocationService();
    }

    public synchronized void fini() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        googleApiClient.disconnect();
        INSTANCE = null;
    }
    public static RCLocationManager getInstance() {
        return INSTANCE;
    }
    private RCLocationManager(RoadCompanionMainBaseActivity context, Locale locale) {
        this.context = context;
        this.locale = locale;
        this.pointsOfInterest.setAmenityList(new ArrayList<Amenity>());
        this.pointsOfInterest.setCheckpostList(new ArrayList<Checkpost>());
        this.pointsOfInterest.setTollList(new ArrayList<Toll>());

    }
    private synchronized void initializeLocationService() {
        googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (context.getDataCollectorService() != null) {
            context.getDataCollectorService().showVehicleStoppedNotification();
        }
        updateLastAddress();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        float speed = 0;
        if (location.hasSpeed()) {
            speed = location.getSpeed() * 3600/1000;
        }
        else {
            float distance = lastLocation.distanceTo(location);
            float seconds = (location.getTime() - lastLocation.getTime());
            seconds = seconds/1000;
            speed = (distance / seconds) * (3600/1000);

        }
        lastLocation = location;
        context.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    TextView tv = (TextView)context.findViewById(R.id.speed);
                    if (tv != null) {

                        tv.setText(String.valueOf(lastLocation.getSpeed()));
                    }
                }
            });
        if (vehicleMoving && speed < 5.0) {
            RCSensorManager.getInstance().adjustVerticalDirection();
            vehicleMoving = false;
            if (context.getDataCollectorService()!= null) {
                context.getDataCollectorService().showVehicleStoppedNotification();
            }
            // Show alert
        }
        else if (!vehicleMoving && speed > 5.0) {
            vehicleMoving = true;
            context.getDataCollectorService().cancelVehicleStoppedNotification();
        }
        updateLastAddress();
    }
    public Location getLastLocation() {
        return lastLocation;
    }
    public Address getLastAddress() {
        return lastAddress;
    }
    public void pause() {

        if (googleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
    public void resume() {

        if (googleApiClient.isConnected() ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }
    private void updateLastAddress() {

        Geocoder geocoder = new Geocoder(context, locale);
        try {
            List<Address> la = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 5);
            if (la.size() > 0) {
                lastAddress = la.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MemberAndVehicles getCurrentMemberAndVehicles() {
        return currentMemberAndVehicles;
    }

    public void setCurrentMemberAndVehicles(MemberAndVehicles currentMemberAndVehicles) {
        this.currentMemberAndVehicles = currentMemberAndVehicles;
    }

    public void setPointsOfInterest(PointsOfInterest poi) {
        this.pointsOfInterest = poi;
    }
    public PointsOfInterest getPointsOfInterest() {
        return this.pointsOfInterest;
    }

    public Boolean getVehicleMoving() {
        return vehicleMoving;
    }

    public void setVehicleMoving(Boolean vehicleMoving) {
        this.vehicleMoving = vehicleMoving;
    }
}
