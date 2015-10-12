package com.khanakirana.admin.khanakirana.activities;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.common.KKConstants;

/**
 * Created by vavasthi on 12/10/15.
 */
public class KKMapPolygonSelectionActivity extends FragmentActivity {
    private GoogleMap googleMap;

    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            latitude = b.getDouble(KKConstants.LATITUDE_TO_CENTER_ON);
            longitude = b.getDouble(KKConstants.LONGITUDE_TO_CENTER_ON);
        }
        setContentView(R.layout.kk_map_polygon_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.kk_map_polygon_view);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), googleMap.getMaxZoomLevel())));
                UiSettings uis =  googleMap.getUiSettings();
                uis.setCompassEnabled(Boolean.TRUE);
                uis.setZoomControlsEnabled(Boolean.TRUE);
            }
        });
    }
}
