package com.avasthi.apps.android.roadsurfacesensing;

public class AccelerationSummary {

	public AccelerationSummary(float xMean, float yMean, float zMean,
			double xSd, double ySd, double zSd) {
		super();
		this.xMean = xMean;
		this.yMean = yMean;
		this.zMean = zMean;
		this.xSd = xSd;
		this.ySd = ySd;
		this.zSd = zSd;
	}
	float xMean;
	float yMean;
	float zMean;
	double xSd;
	double ySd;
	double zSd;
}
