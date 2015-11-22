package com.avasthi.roadcompanion.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.common.KKConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 12/10/15.
 */
public class KKMapPolygonSelectionActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap googleMap;
    List<LatLng> polygonPoints;
    Boolean polygonFormed = false;
    Polygon polygon;
    Polyline polyline;

    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        polygonPoints = new ArrayList<>();
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
                KKMapPolygonSelectionActivity.this.googleMap = googleMap;
                float zoomLevel = (googleMap.getMaxZoomLevel() - googleMap.getMinZoomLevel()) * 0.75F;
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(latitude, longitude), zoomLevel)));
                UiSettings uis = googleMap.getUiSettings();
                uis.setCompassEnabled(Boolean.TRUE);
                uis.setZoomControlsEnabled(Boolean.TRUE);
                googleMap.setOnMapClickListener(KKMapPolygonSelectionActivity.this);
                googleMap.setOnMapLongClickListener(KKMapPolygonSelectionActivity.this);
            }
        });
    }
    @Override
    public void onMapClick(LatLng latLng) {
        checkForPolygonCompletion(latLng);
        drawShape();
    }
    @Override
    public void onMapLongClick(LatLng latLng) {

        checkForPolygonCompletion(latLng);
        drawShape();

    }
    private void drawShape() {
        googleMap.clear();
        if (polygonFormed) {

            PolygonOptions po = new PolygonOptions();
            po.addAll(polygonPoints);
            po.fillColor(Color.CYAN);
            po.strokeColor(Color.BLUE);
            po.strokeWidth(5);
            googleMap.addPolygon(po);
        }
        else {

            PolylineOptions po = new PolylineOptions();
            po.addAll(polygonPoints);
            po.color(Color.BLUE);
            po.width(5);
            googleMap.addPolyline(po);
        }
    }
    private void checkForPolygonCompletion(LatLng latLng) {

        if (polygonPoints.size() > 2) {

            float minDistance = 50F;
            LatLng firstPoint = polygonPoints.get(0);
            float[] result = new float[2];
            Location.distanceBetween(firstPoint.latitude, firstPoint.longitude, latLng.latitude, latLng.longitude, result);
            if (Math.abs(result[0]) < minDistance) {
                polygonFormed = true;
                return;
            }
        }
        polygonPoints.add(latLng);
    }
}
