package com.avasthi.roadcompanion.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by vavasthi on 3/11/15.
 */
public class RoadCompanionSensorManager implements SensorEventListener {

    private static RoadCompanionSensorManager INSTANCE;

    private SensorManager sensorManager;

    private Activity context;

    public static final float EPSILON = 0.000000001f;

    // angular speeds from gyro
    private float[] gyro = new float[3];

    // rotation matrix from gyro data
    private float[] gyroMatrix = new float[9];

    // orientation angles from gyro matrix
    private float[] gyroOrientation = new float[3];

    // magnetic field vector
    private float[] magnet = new float[3];

    // accelerometer vector
    private float[] accel = new float[3];

    // orientation angles from accel and magnet
    private float[] accMagOrientation = new float[3];

    // final orientation angles from sensor fusion
    private float[] fusedOrientation = new float[3];

    // accelerometer and magnetometer based rotation matrix
    private float[] rotationMatrix = new float[9];

    private RoadCompanionSensorManager(Activity context) {
        this.context = context;
        this.sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        gyroOrientation[0] = 0.0f;
        gyroOrientation[1] = 0.0f;
        gyroOrientation[2] = 0.0f;

        // initialise gyroMatrix with identity matrix
        gyroMatrix[0] = 1.0f; gyroMatrix[1] = 0.0f; gyroMatrix[2] = 0.0f;
        gyroMatrix[3] = 0.0f; gyroMatrix[4] = 1.0f; gyroMatrix[5] = 0.0f;
        gyroMatrix[6] = 0.0f; gyroMatrix[7] = 0.0f; gyroMatrix[8] = 1.0f;
    }

    private void registerListeners() {

        int samplingPeriodMicroSeconds = 1000 * 1000 / 10;
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                samplingPeriodMicroSeconds);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                samplingPeriodMicroSeconds);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                samplingPeriodMicroSeconds);
    }
    private void calculateOrientation() {
        float[] rotationMatrix = new float[16];
        
    }
    private void unregisterListeners() {
        sensorManager.unregisterListener(this);
    }

    public void pause() {
        registerListeners();
    }
    public void resume() {
        unregisterListeners();
    }

    private void getRotationVectorFromGyro(float[] gyroValues,
                                           float[] deltaRotationVector,
                                           float timeFactor) {
        float[] normValues = new float[3];

        // Calculate the angular speed of the sample
        float omegaMagnitude =
                (float)Math.sqrt(gyroValues[0] * gyroValues[0] +
                        gyroValues[1] * gyroValues[1] +
                        gyroValues[2] * gyroValues[2]);

        // Normalize the rotation vector if it's big enough to get the axis
        if(omegaMagnitude > EPSILON) {
            normValues[0] = gyroValues[0] / omegaMagnitude;
            normValues[1] = gyroValues[1] / omegaMagnitude;
            normValues[2] = gyroValues[2] / omegaMagnitude;
        }

        // Integrate around this axis with the angular speed by the timestep
        // in order to get a delta rotation from this sample over the timestep
        // We will convert this axis-angle representation of the delta rotation
        // into a quaternion before turning it into the rotation matrix.
        float thetaOverTwo = omegaMagnitude * timeFactor;
        float sinThetaOverTwo = (float)Math.sin(thetaOverTwo);
        float cosThetaOverTwo = (float)Math.cos(thetaOverTwo);
        deltaRotationVector[0] = sinThetaOverTwo * normValues[0];
        deltaRotationVector[1] = sinThetaOverTwo * normValues[1];
        deltaRotationVector[2] = sinThetaOverTwo * normValues[2];
        deltaRotationVector[3] = cosThetaOverTwo;
    }
    public static void init(Activity context) {
        INSTANCE = new RoadCompanionSensorManager(context);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
