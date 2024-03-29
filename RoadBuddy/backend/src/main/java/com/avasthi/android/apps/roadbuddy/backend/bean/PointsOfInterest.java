package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.List;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class PointsOfInterest {
    public PointsOfInterest(Drive currentDrive, List<Amenity> amenityList, List<Toll> tollList, List<Checkpost> checkpostList) {
        this.currentDrive = currentDrive;
        this.amenityList = amenityList;
        this.checkpostList = checkpostList;
        this.tollList = tollList;
    }

    public PointsOfInterest() {
    }

    public List<Amenity> getAmenityList() {
        return amenityList;
    }

    public void setAmenityList(List<Amenity> amenityList) {
        this.amenityList = amenityList;
    }

    public List<Checkpost> getCheckpostList() {
        return checkpostList;
    }

    public void setCheckpostList(List<Checkpost> checkpostList) {
        this.checkpostList = checkpostList;
    }

    public List<Toll> getTollList() {
        return tollList;
    }

    public void setTollList(List<Toll> tollList) {
        this.tollList = tollList;
    }

    public Drive getCurrentDrive() {
        return currentDrive;
    }

    public void setCurrentDrive(Drive currentDrive) {
        this.currentDrive = currentDrive;
    }

    private Drive currentDrive;
    private List<Amenity> amenityList;
    private List<Toll> tollList;
    private List<Checkpost> checkpostList;
}
