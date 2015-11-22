package com.avasthi.roadcompanion.backend.fence;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Created by vavasthi on 12/10/15.
 */
public class FencingPolygon extends Polygon {
    private long ID;

    public FencingPolygon(LinearRing shell, LinearRing[] holes, GeometryFactory factory, long ID) {
        super(shell, holes, factory);
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
