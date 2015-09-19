package com.avasthi.apps.android.roadsurfacesensing;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.support.v4.util.Pair;

public class HistoricalSensorDataQueue {
	
	private HistoricalSensorDataQueue() {
		
		accelerationData = new ConcurrentLinkedQueue< Pair<Date, AccelerationSummary> >();
	}
    public static HistoricalSensorDataQueue getInstance() {
        if (self == null) {
            synchronized(HistoricalSensorDataQueue.class) {
                if (self == null) {
                    self = new HistoricalSensorDataQueue();
                }
            }
        }
        return self;
    }
    public void addAccelerationData(Date timestamp, SensorRecord sr) {
    	StatisticalSummary ssx = summary(sr.x.toArray(new Float[sr.x.size()]));
    	StatisticalSummary ssy = summary(sr.y.toArray(new Float[sr.y.size()]));
    	StatisticalSummary ssz = summary(sr.z.toArray(new Float[sr.z.size()]));
    	accelerationData.add(new Pair<Date, AccelerationSummary>(timestamp, 
    			new AccelerationSummary(ssx.mean, ssy.mean, ssz.mean, ssx.sd, ssy.sd, ssz.sd)));
    }
    private StatisticalSummary summary(Float[] values) {
    	float mean = 0.0f;
    	double sd = 0.0f;
    	for (int i = 0; i < values.length; ++i) {
    		mean += values[i];
    	}
    	mean /= values.length;
    	for (int i = 0; i < values.length; ++i) {
    		float tmp = (values[i] - mean);
    		sd += (tmp * tmp);
    	}
    	sd /= (values.length - 1);
    	sd = Math.sqrt(sd);
    	return new StatisticalSummary(mean, sd);
    }
    private static HistoricalSensorDataQueue self = null;
	ConcurrentLinkedQueue< Pair<Date, AccelerationSummary> > accelerationData;

}
