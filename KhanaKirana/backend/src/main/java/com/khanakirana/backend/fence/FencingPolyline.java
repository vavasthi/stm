package com.khanakirana.backend.fence;

/**
 * Created by vavasthi on 12/10/15.
 */
public class FencingPolyline {
    public double[][] coordinates;

    public FencingPolyline() {};

    public FencingPolyline(double[][] coordinates){
        this.coordinates = coordinates;
    }

    public double[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
    }
}
