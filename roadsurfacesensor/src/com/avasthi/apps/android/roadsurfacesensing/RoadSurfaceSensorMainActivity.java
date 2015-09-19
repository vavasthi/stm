package com.avasthi.apps.android.roadsurfacesensing;

import java.util.Date;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class RoadSurfaceSensorMainActivity extends Activity {

	SensorReading rotationVector;
	SensorReading linearAcceleration;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_road_surface_sensor);

/*		final View controlsView = findViewById(R.id.top_content_controls);
		final View contentView = findViewById(R.id.bottom_content_controls);*/

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		// findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
		SensorInterface.init(this);
	}

	public void updateSensor(Date timestamp, Sensor sensor, float[] values) {
		switch (sensor.getType()) {
		case Sensor.TYPE_ROTATION_VECTOR:
			rotationVector = new SensorReading(sensor, values);
			updateRotationVectorUI(values);
			break;
		case Sensor.TYPE_LINEAR_ACCELERATION:
			linearAcceleration = new SensorReading(sensor, values);
			updateLinearAccelerationUI(values);
			break;
		case Sensor.TYPE_ACCELEROMETER:
			linearAcceleration = new SensorReading(sensor, values);
			updateLinearAccelerationWithGUI(values);
			break;
		}
	}

	private void updateRotationVectorUI(final float[] values) {

		TextView orientation_x = (TextView) findViewById(R.id.orientation_x);
		TextView orientation_y = (TextView) findViewById(R.id.orientation_y);
		TextView orientation_z = (TextView) findViewById(R.id.orientation_z);

		TextView orientation_r_x = (TextView) findViewById(R.id.orientation_r_x);
		TextView orientation_r_y = (TextView) findViewById(R.id.orientation_r_y);
		TextView orientation_r_z = (TextView) findViewById(R.id.orientation_r_z);
		
		float[] r_values = this.rotationVectorAction(values);
		
		orientation_x.setText(String.format("%.02f",values[0]));
		orientation_y.setText(String.format("%.02f",values[1]));
		orientation_z.setText(String.format("%.02f",values[2]));

		orientation_r_x.setText(String.format("%.02f",r_values[0]));
		orientation_r_y.setText(String.format("%.02f",r_values[1]));
		orientation_r_z.setText(String.format("%.02f",r_values[2]));
	}

	private void updateLinearAccelerationUI(final float[] values) {

		TextView la_x = (TextView) findViewById(R.id.acceleration_x);
		TextView la_y = (TextView) findViewById(R.id.acceleration_y);
		TextView la_z = (TextView) findViewById(R.id.acceleration_z);
		la_x.setText(String.format("%.02f",values[0]));
		la_y.setText(String.format("%.02f",values[1]));
		la_z.setText(String.format("%.02f",values[2]));
	}
	private void updateLinearAccelerationWithGUI(final float[] values) {

		TextView la_g_x = (TextView) findViewById(R.id.acceleration_g_x);
		TextView la_g_y = (TextView) findViewById(R.id.acceleration_g_y);
		TextView la_g_z = (TextView) findViewById(R.id.acceleration_g_z);
		la_g_x.setText(String.format("%.02f",values[0]));
		la_g_y.setText(String.format("%.02f",values[1]));
		la_g_z.setText(String.format("%.02f",values[2]));
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		SensorInterface.fini();
	}
	private float[] rotationVectorAction(float[] values) 
	{
	    float[] result = new float[3];
	    float vec[] = values;
	    float[] orientation = new float[3];
	    float[] rotMat = new float[9];
	    SensorManager.getRotationMatrixFromVector(rotMat, vec);
	    SensorManager.getOrientation(rotMat, orientation);
	    result[0] = (float) orientation[0]; //Yaw
	    result[1] = (float) orientation[1]; //Pitch
	    result[2] = (float) orientation[2]; //Roll
	    return result;
	}
	
}
