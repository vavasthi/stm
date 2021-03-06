package com.sanjnan.vitarak.app.customer.utils;

import android.app.Activity;
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
public class KKLocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private Locale locale;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Address lastAddress;
    private Activity context;

    private static KKLocationManager INSTANCE;

    public static synchronized  void initialize(Activity context, Locale locale) {
        INSTANCE = new KKLocationManager(context, locale);
        INSTANCE.initializeLocationService();
    }

    public static KKLocationManager getInstance() {
        return INSTANCE;
    }
    private KKLocationManager(Activity context, Locale locale) {
        this.context = context;
        this.locale = locale;
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
        lastLocation = location;
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
}
