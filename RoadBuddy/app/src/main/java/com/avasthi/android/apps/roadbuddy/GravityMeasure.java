package com.avasthi.android.apps.roadbuddy;

/**
 * Created by vavasthi on 15/3/15.
 */
public class GravityMeasure {

    GravityMeasure(float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
    }
    float x;
    float y;
    float z;
}
