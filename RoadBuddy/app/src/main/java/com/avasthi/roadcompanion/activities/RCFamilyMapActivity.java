package com.avasthi.roadcompanion.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.FamilyMemberAndPointsOfInterest;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.background.tasks.RCGetFamilyDriveTask;
import com.avasthi.roadcompanion.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 20/12/15.
 */
public class RCFamilyMapActivity extends FragmentActivity {
    private GoogleMap googleMap;
    private List<FamilyMemberAndPointsOfInterest> familyMemberAndPointsOfInterestList;
    private double latitude;
    private double longitude;
    private BroadcastReceiver broadcastReceiver;
    Marker[] markers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        new RCGetFamilyDriveTask(this, false).execute();
    }
    public void initializeMap() {

        setContentView(R.layout.family_map_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.family_map_fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                RCFamilyMapActivity.this.googleMap = googleMap;
                UiSettings uis = googleMap.getUiSettings();
                uis.setCompassEnabled(Boolean.TRUE);
                uis.setZoomControlsEnabled(Boolean.TRUE);
                updateMarkers();
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                new RCGetFamilyDriveTask(RCFamilyMapActivity.this, true).execute();
            }
        };
;
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constants.MAP_UPDATE_EVENT_NAME));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {

            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
    }

    private void updateMarkers() {


        if (markers != null) {
            for (Marker m : markers) {
                m.remove();
            }
        }
        List<Marker> markerList = new ArrayList<>();
        if (familyMemberAndPointsOfInterestList.size() != 0) {

            markers = new Marker[familyMemberAndPointsOfInterestList.size()];
            int count = 0;
            for (FamilyMemberAndPointsOfInterest fmapoi : familyMemberAndPointsOfInterestList) {
                if (fmapoi.getPointsOfInterest().getCurrentDrive() != null) {

                    LatLng ll = new LatLng(fmapoi.getPointsOfInterest().getCurrentDrive().getLastLatitude(),
                            fmapoi.getPointsOfInterest().getCurrentDrive().getLastLongitude());
                    markerList.add(googleMap.addMarker(new MarkerOptions().position(ll)
                                    .title(fmapoi.getFamilyMember().getName())
                                    .snippet(fmapoi.getFamilyMember().getPrimaryVehicleId().toString())
                    ));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(ll).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
        else {
            googleMap.setMyLocationEnabled(true);
        }
        if (markerList.size() == 0) {
            markers = new Marker[0];
        }
        else {
            markers = markerList.toArray(new Marker[markerList.size()]);
        }
    }
    public void updateFamilyMemberAndPointsOfInterestList(List<FamilyMemberAndPointsOfInterest> familyMemberAndPointsOfInterestList) {
        this.familyMemberAndPointsOfInterestList = familyMemberAndPointsOfInterestList;
        if (familyMemberAndPointsOfInterestList != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateMarkers();
                }
            });
        }
    }
    public void updateFamilyMemberAndPointsOfInterestListAndInitializeMap(List<FamilyMemberAndPointsOfInterest> familyMemberAndPointsOfInterestList) {
        this.familyMemberAndPointsOfInterestList = familyMemberAndPointsOfInterestList;
        if (familyMemberAndPointsOfInterestList != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initializeMap();
                }
            });
        }
    }
}
