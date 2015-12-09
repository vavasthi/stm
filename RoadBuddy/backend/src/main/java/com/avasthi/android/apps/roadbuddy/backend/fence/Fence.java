package com.avasthi.android.apps.roadbuddy.backend.fence;

/**
 * Created by vavasthi on 9/12/15.
 */
public class Fence {

    public Fence() {};

    public Fence(long id, long placeId, String name, String group, Double latitude, Double longitude, Double radius) {
        this.id = id;
        this.placeId = placeId;
        this.name = name;
        this.group = group;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public Fence(long placeId, String name, String group, Double latitude, Double longitude, Double radius) {
        this.placeId = placeId;
        this.name = name;
        this.group = group;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    private long id = -1;
    private long placeId;
    private  String name;
    private String group;
    private Double latitude;
    private Double longitude;
    private Double radius;
}
