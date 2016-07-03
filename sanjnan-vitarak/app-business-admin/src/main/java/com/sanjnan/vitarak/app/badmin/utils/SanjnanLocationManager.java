package com.sanjnan.vitarak.app.badmin.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

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
public class SanjnanLocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleApiClient googleApiClient;
    private Locale locale;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Address lastAddress;
    private Activity context;

    private boolean locationServiceAvailable = false;

    private static SanjnanLocationManager INSTANCE;

    public static synchronized  void initialize(Activity context, Locale locale) {
        INSTANCE = new SanjnanLocationManager(context, locale);
        INSTANCE.initializeLocationService();
    }

    public static SanjnanLocationManager getInstance() {
        return INSTANCE;
    }
    private SanjnanLocationManager(Activity context, Locale locale) {
        this.context = context;
        this.locale = locale;
    }
    private synchronized void initializeLocationService() {
        boolean coarseLocationNeedsToBeRequested = false;
        boolean fineLocationNeedsToBeRequested = false;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            coarseLocationNeedsToBeRequested = true;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fineLocationNeedsToBeRequested = true;
        }
        if (coarseLocationNeedsToBeRequested || fineLocationNeedsToBeRequested) {

            List<String> permissionList = new ArrayList<>();
            if (coarseLocationNeedsToBeRequested) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (fineLocationNeedsToBeRequested) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            ActivityCompat.requestPermissions(context, permissionList.toArray(new String[permissionList.size()]),
                    PermissionRequestConstants.LOCATION_REQUEST);
        }
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

        if (googleApiClient != null && googleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
    public void resume() {

        if (googleApiClient != null && googleApiClient.isConnected() ) {
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

    public boolean isLocationServiceAvailable() {
        return locationServiceAvailable;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PermissionRequestConstants.LOCATION_REQUEST: {

                boolean coarseLocationGranted = false;
                boolean fineLocationGranted = false;
                for (int i = 0; i < permissions.length; ++i) {
                    if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                            grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        coarseLocationGranted = true;
                    } else if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                            grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fineLocationGranted = true;
                    }
                }
                if (fineLocationGranted || coarseLocationGranted) {

                    googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
                    googleApiClient.connect();
                    locationRequest = new LocationRequest();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(5000);
                    if (fineLocationGranted) {

                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    } else {

                        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    }
                    locationServiceAvailable = true;
                }
            }
            break;
        }

    }
}
