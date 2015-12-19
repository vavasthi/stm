package com.avasthi.roadcompanion.utils;

import android.location.Location;

import com.avasthi.roadcompanion.StatisticsException;
import com.avasthi.roadcompanion.StatisticsPack;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;

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
    private int seconds = -1;

    public RCSummarizedData addData(float accuracy, float bearing, double latitude, double longitude, float speed, float verticalAccelerometer) {
        if (seconds == -1) {
            seconds = Calendar.getInstance().get(Calendar.SECOND) / 10;
        }
        int oldSeconds = seconds;
        List<RCSensorData> oldList = null;
        if (seconds != Calendar.getInstance().get(Calendar.SECOND) / 10) {
            oldList = dataList;
            dataList = new ArrayList<>();
            seconds = Calendar.getInstance().get(Calendar.SECOND) / 10;
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
                    c.set(Calendar.SECOND,oldSeconds * 10);
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
            Double lastLatitude = RCLocationManager.getInstance().getPointsOfInterest().getCurrentDrive().getLastLatitude();
            Double lastLongitude = RCLocationManager.getInstance().getPointsOfInterest().getCurrentDrive().getLastLongitude();
            Double currentLatitude = StatisticsPack.median(rLatitude);
            Double currentLongitude = StatisticsPack.median(rLongitude);
            float[] distance = new float[3];
            Location.distanceBetween(lastLatitude, lastLongitude, currentLatitude, currentLongitude, distance);
            return new RCSummarizedData(0L,
                    timestamp,
                    RCLocationManager.getInstance().getCurrentMemberAndVehicles().getMember().getId(),
                    RCLocationManager.getInstance().getPointsOfInterest().getCurrentDrive().getId(),
                    StatisticsPack.mean(rAccuracy),
                    StatisticsPack.median(rBearing),
                    currentLatitude,
                    currentLongitude,
                    StatisticsPack.mean(rSpeed),
                    StatisticsPack.mean(rVerticalAccelerometer),
                    StatisticsPack.stddev(rVerticalAccelerometer),
                    distance[0]);
        } catch (StatisticsException e) {
            e.printStackTrace();
        }
        return null;
    }
    public RCSummarizedData addData(Location location, float accel) {
        return addData(location.getAccuracy(), location.getBearing(), location.getLatitude(), location.getLongitude(), location.getSpeed(), accel);
    }
}
