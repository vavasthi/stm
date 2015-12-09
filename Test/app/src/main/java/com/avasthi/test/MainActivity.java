package com.avasthi.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;

    TextView x;
    TextView y;
    TextView z;
    TextView nx;
    TextView ny;
    TextView nz;
    TextView gx;
    TextView gy;
    TextView gz;
    TextView name;

    float[] lastAccelerometer;
    float[] lastMagnetic;
    float[] lastGyro;
    float[] lastRotationMatrix;
    float[] lastOrientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookInterface.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        x = (TextView)findViewById(R.id.tvx);
        y = (TextView)findViewById(R.id.tvy);
        z = (TextView)findViewById(R.id.tvz);
        gx = (TextView)findViewById(R.id.nx);
        gy = (TextView)findViewById(R.id.ny);
        gz = (TextView)findViewById(R.id.nz);
        nx = (TextView)findViewById(R.id.gx);
        ny = (TextView)findViewById(R.id.gy);
        nz = (TextView)findViewById(R.id.gz);
        registerListeners();

    }
    private void registerListeners() {

        int samplingPeriodMicroSeconds = 1000 * 1000 / 10;
        List<Sensor> l = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String str = "";
        for (Sensor s : l) {
            str += s.getName() + "." + s.toString() + "|";
        }
        x.setText(str);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                samplingPeriodMicroSeconds);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                samplingPeriodMicroSeconds);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                samplingPeriodMicroSeconds);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                samplingPeriodMicroSeconds);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
                samplingPeriodMicroSeconds);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] v = event.values;
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = v;
                x.setText(String.valueOf(v[0]));
                y.setText(String.valueOf(v[1]));
                z.setText(String.valueOf(v[2]));
            case Sensor.TYPE_GYROSCOPE:
                lastGyro = v;
                if (lastMagnetic != null && lastAccelerometer != null) {

                    float[] r = new float[9];
                    float[] i = new float[9];
                    if (SensorManager.getRotationMatrix(r, i, lastAccelerometer, lastMagnetic)) {

                        v = SensorManager.getOrientation(r, v);
                    }
                }
                gx.setText(String.valueOf(v[0]));
                gy.setText(String.valueOf(v[1]));
                gz.setText(String.valueOf(v[2]));
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastMagnetic = v;
            case Sensor.TYPE_ROTATION_VECTOR:
                lastRotationMatrix = v;
                float[] rotationMatrixFromVector = new float[9];
                float[] r = new float[9];
                float[] i = new float[9];
                if (SensorManager.getRotationMatrix(r, i, lastAccelerometer, lastMagnetic)) {

                    v = SensorManager.getOrientation(r, v);
                }
                SensorManager.getRotationMatrixFromVector(r, event.values);
                lastOrientation = SensorManager.getOrientation(r, lastOrientation);
                nx.setText(String.valueOf((float)Math.toDegrees(lastOrientation[0])));
                ny.setText(String.valueOf((float)Math.toDegrees(lastOrientation[1])));
                nz.setText(String.valueOf((float)Math.toDegrees(lastOrientation[2])));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
