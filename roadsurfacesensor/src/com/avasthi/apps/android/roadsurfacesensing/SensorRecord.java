package com.avasthi.apps.android.roadsurfacesensing;

import java.util.ArrayList;
import java.util.List;

public class SensorRecord {

	/* The sensor values are returned as a vector of three floats. We find out x, y and z 
	 *  by looking as a value that is closer to 9
	 */
	List<Float> x = new ArrayList<Float>();
	List<Float> y = new ArrayList<Float>();
	List<Float> z = new ArrayList<Float>();
	
}
