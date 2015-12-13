package com.avasthi.roadcompanion.utils;

import android.location.Location;

import com.avasthi.roadcompanion.StatisticsException;
import com.avasthi.roadcompanion.StatisticsPack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vavasthi on 13/12/15.
 */
public class RCDataManager {
    public final static RCDataManager INSTANCE = new RCDataManager();
    private List<RCSensorData> dataList = new ArrayList<>();
    private int minutes = -1;

    public RCSummarizedData addData(float accuracy, float bearing, double latitude, double longitude, float speed, float verticalAccelerometer) {
        if (minutes == -1) {
            minutes = Calendar.getInstance().get(Calendar.MINUTE);
        }
        List<RCSensorData> oldList = null;
        if (minutes != Calendar.getInstance().get(Calendar.MINUTE)) {
            oldList = dataList;
            dataList = new ArrayList<>();
            minutes = Calendar.getInstance().get(Calendar.MINUTE);
        }
        dataList.add(new RCSensorData(accuracy, bearing, latitude, longitude, speed, verticalAccelerometer));
        if (oldList == null || oldList.size() == 0) {
            return null;
        }
        float[] rAccuracy = new float[oldList.size()];
        float[] rBearing = new float[oldList.size()];
        double[] rLatitude = new double[oldList.size()];
        double[] rLongitude = new double[oldList.size()];
        float[] rSpeed = new float[oldList.size()];
        float[] rVerticalAccelerometer = new float[oldList.size()];
        Date timestamp = null;
        try {
            int i = 0;
            for (RCSensorData rcsd : oldList) {
                if (timestamp == null) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(rcsd.getTimestamp());
                    c.set(Calendar.MILLISECOND, 0);
                    c.set(Calendar.SECOND,0);
                    timestamp = c.getTime();
                }
                rAccuracy[i] = rcsd.getAccuracy();
                rBearing[i] = rcsd.getBearing();
                rLatitude[i] = rcsd.getLatitude();
                rLongitude[i] = rcsd.getLongitude();
                rSpeed[i] = rcsd.getSpeed();
                rVerticalAccelerometer[i] = rcsd.getVerticalAccelerometer();
                ++i;
            }
            return new RCSummarizedData(0L, timestamp, StatisticsPack.mean(rAccuracy), StatisticsPack.median(rBearing), StatisticsPack.median(rLatitude), StatisticsPack.median(rLongitude), StatisticsPack.mean(rSpeed), StatisticsPack.mean(rVerticalAccelerometer), StatisticsPack.stddev(rVerticalAccelerometer));
        } catch (StatisticsException e) {
            e.printStackTrace();
        }
        return null;
    }
    public RCSummarizedData addData(Location location, float accel) {
        return addData(location.getAccuracy(), location.getBearing(), location.getLatitude(), location.getLongitude(), location.getSpeed(), accel);
    }
}
