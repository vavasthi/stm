package com.avasthi.android.apps.roadbuddy;

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
