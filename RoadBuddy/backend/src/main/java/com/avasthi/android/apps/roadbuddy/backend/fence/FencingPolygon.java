package com.avasthi.android.apps.roadbuddy.backend.fence;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Created by vavasthi on 17/12/15.
 */
public class FencingPolygon extends Polygon {
    private long id;
    private long placeId;

    public FencingPolygon(long id, long placeId, double latitude, double longitude, double radius) {
        super(new GeometryFactory().createLinearRing(getCoordinates(latitude, longitude, radius)), null, new GeometryFactory());
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    private static double getAngularUnits(double distanceInKilometers) {
        return (distanceInKilometers * 180 * 1000)/(Math.PI * 6378137);
    }
    private static Coordinate[] getCoordinates(double latitude, double longitude, double radius) {
        Coordinate[] coordinates = new Coordinate[5];
        double delta = getAngularUnits(radius);
        coordinates[0] = new Coordinate(longitude - delta, latitude - delta);
        coordinates[1] = new Coordinate(longitude - delta, latitude + delta);
        coordinates[2] = new Coordinate(longitude + delta, latitude - delta);
        coordinates[3] = new Coordinate(longitude + delta, latitude + delta);
        coordinates[4] = new Coordinate(longitude - delta, latitude - delta);
        return coordinates;
    }
}
