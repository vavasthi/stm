package com.avasthi.apps.android.roadsurfacesensing;

import android.hardware.Sensor;

import java.util.Date;

public class SensorReading {

	Sensor sensor;
	float[] values;
	public SensorReading(Sensor sensor, float[] values) {
		this.sensor = sensor;
		this.values = values;
	}
}
