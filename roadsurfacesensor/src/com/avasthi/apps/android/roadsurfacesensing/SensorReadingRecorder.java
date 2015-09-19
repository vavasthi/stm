package com.avasthi.apps.android.roadsurfacesensing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SensorReadingRecorder {

	SensorReadingRecorder() {
		timestamp = new Date();
		acceleration = new SensorRecord();
	}
	public void addAcceleration(Date timestamp, float x, float y, float z) {
		Date tmp = getTimeTillSecs(timestamp);
		if (!tmp.equals(this.timestamp)) {
			HistoricalSensorDataQueue.getInstance().addAccelerationData(this.timestamp, acceleration);
			this.timestamp = tmp;
			acceleration = new SensorRecord();
		}
	    acceleration.x.add(x);
		acceleration.y.add(y);
		acceleration.z.add(z);
	}
	private Date getTimeTillSecs(Date timestamp) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(timestamp);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	Date timestamp;
	SensorRecord acceleration;
}
