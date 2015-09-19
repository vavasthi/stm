package com.avasthi.apps.android.roadsurfacesensing;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SensorInterface implements SensorEventListener, LocationListener {

    private LocationManager locationManager_;
    static private SensorInterface self = null;

    private RoadSurfaceSensorMainActivity mainActivity;
    
    private SensorInterface(RoadSurfaceSensorMainActivity mainActivity) {

        this.mainActivity = mainActivity;
        locationManager_
                = (LocationManager)mainActivity.getSystemService(Context.LOCATION_SERVICE);
        locationManager_.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
        // Get the reference to the sensor manager
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) mainActivity.getSystemService(Service.SENSOR_SERVICE);
        // Get the list of sensor
        registerSensorListeners(sensorManager);
    }

    public static void init(RoadSurfaceSensorMainActivity mainActivity) {
        if (self == null) {
            synchronized(SensorInterface.class) {
                if (self == null) {
                    self = new SensorInterface(mainActivity);
                }
            }
        }
        else {
        	self = new SensorInterface(mainActivity);
        }
    }
    public static void fini() {
    	self.locationManager_.removeUpdates(self);
    	// Get the reference to the sensor manager
    	android.hardware.SensorManager sensorManager 
    		= (android.hardware.SensorManager) self.mainActivity.getSystemService(Service.SENSOR_SERVICE);
    	// Get the list of senso
    	sensorManager.unregisterListener(self);
    	self = null;
    }
    public static SensorInterface getInstance() {
        return self;
    }
	void registerSensorListeners(android.hardware.SensorManager sm) {
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
        mainActivity.updateSensor(new Date(), event.sensor, event.values);
/*		ListView lv = (ListView) findViewById(R.id.listView1);
		SensorDetailAdapter sda = (SensorDetailAdapter)lv.getAdapter();
		sda.setValues(event.sensor, event.values);
		sda.setLocation(locationManager_.getLastKnownLocation(LOCATION_SERVICE));*/
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.v(Context.LOCATION_SERVICE, "Location change receieved " + location.getLatitude() + "," + location.getLongitude());
/*		ListView lv = (ListView) findViewById(R.id.listView1);
		SensorDetailAdapter sda = (SensorDetailAdapter)lv.getAdapter();
		sda.setLocation(location);*/
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
