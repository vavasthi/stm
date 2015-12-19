package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 19/12/15.
 */
@Entity
public class DriveParameters extends AbstractEntity {
    public DriveParameters(Long driveId, Float foodAmount, Float fuelAmount, Integer numberOfAmenityStops, Integer numberOfCheckposts, Integer numberOfSpeedCameras, Float fineAmount, Integer numberOfTollStops, Float tollAmount) {
        this.driveId = driveId;
        this.foodAmount = foodAmount;
        this.fuelAmount = fuelAmount;
        this.numberOfAmenityStops = numberOfAmenityStops;
        this.numberOfCheckposts = numberOfCheckposts;
        this.numberOfSpeedCameras = numberOfSpeedCameras;
        this.fineAmount = fineAmount;
        this.numberOfTollStops = numberOfTollStops;
        this.tollAmount = tollAmount;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public Float getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Float foodAmount) {
        this.foodAmount = foodAmount;
    }

    public Float getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Float fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public Integer getNumberOfAmenityStops() {
        return numberOfAmenityStops;
    }

    public void setNumberOfAmenityStops(Integer numberOfAmenityStops) {
        this.numberOfAmenityStops = numberOfAmenityStops;
    }

    public Integer getNumberOfCheckposts() {
        return numberOfCheckposts;
    }

    public void setNumberOfCheckposts(Integer numberOfCheckposts) {
        this.numberOfCheckposts = numberOfCheckposts;
    }

    public Integer getNumberOfTollStops() {
        return numberOfTollStops;
    }

    public void setNumberOfTollStops(Integer numberOfTollStops) {
        this.numberOfTollStops = numberOfTollStops;
    }

    public Float getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(Float tollAmount) {
        this.tollAmount = tollAmount;
    }

    public Float getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Float fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Integer getNumberOfSpeedCameras() {
        return numberOfSpeedCameras;
    }

    public void setNumberOfSpeedCameras(Integer numberOfSpeedCameras) {
        this.numberOfSpeedCameras = numberOfSpeedCameras;
    }

    private Long driveId;
    private Integer numberOfTollStops;
    private Float tollAmount;
    private Integer numberOfCheckposts;
    private Integer numberOfSpeedCameras;
    private Float fineAmount;
    private Integer numberOfAmenityStops;
    private Float fuelAmount;
    private Float foodAmount;
}
