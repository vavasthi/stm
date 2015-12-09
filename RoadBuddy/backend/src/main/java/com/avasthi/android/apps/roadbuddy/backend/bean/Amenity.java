package com.avasthi.android.apps.roadbuddy.backend.bean;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
public class Amenity extends Establishment {

    public Amenity() {

    }
    public Amenity(Long userId, Date timestamp, String name, Double latitude, Double longitude, Boolean hasRestaurant, Boolean hasRestrooms, Boolean hasPetrolStation, String city, String state, String country) {
        super(userId, timestamp, latitude, longitude, city, state, country);
        this.name = name;
        this.hasRestaurant = hasRestaurant;
        this.hasRestrooms = hasRestrooms;
        this.hasPetrolStation = hasPetrolStation;

    }
    public Boolean getHasRestaurant() {
        return hasRestaurant;
    }

    public void setHasRestaurant(Boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
    }

    public Boolean getHasRestrooms() {
        return hasRestrooms;
    }

    public void setHasRestrooms(Boolean hasRestrooms) {
        this.hasRestrooms = hasRestrooms;
    }

    public Boolean getHasPetrolStation() {
        return hasPetrolStation;
    }

    public void setHasPetrolStation(Boolean hasPetrolStation) {
        this.hasPetrolStation = hasPetrolStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private Boolean hasRestaurant;
    private Boolean hasRestrooms;
    private Boolean hasPetrolStation;
}
