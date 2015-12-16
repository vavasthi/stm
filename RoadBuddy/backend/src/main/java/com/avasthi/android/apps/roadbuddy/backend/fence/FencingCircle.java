package com.avasthi.android.apps.roadbuddy.backend.fence;

import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;

/**
 * Created by vavasthi on 9/12/15.
 */
public class FencingCircle implements Serializable {
    public FencingCircle() {
    }

    public FencingCircle(Long id, Long placeId, Geometry geometry) {
        this.id = id;
        this.placeId = placeId;
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    private Geometry geometry;
    private Long id;
    private Long placeId;
}
