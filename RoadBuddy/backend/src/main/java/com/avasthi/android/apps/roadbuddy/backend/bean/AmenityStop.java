package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

import javax.inject.Named;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class AmenityStop extends AbstractStop {

    public AmenityStop() {

    }
    public AmenityStop(Long userId,
                       Long driveId,
                       Long establishmentId,
                       Date timestamp,
                       Integer restaurantRating,
                       Float foodAmount,
                       Integer restroomRating,
                       Integer petrolStationRating,
                       Float fuelAmount,
                       Float fuelQuantity) {
        super(userId, driveId, establishmentId, timestamp);
        this.restaurantRating = restaurantRating;
        this.foodAmount = foodAmount;
        this.restroomRating = restroomRating;
        this.petrolStationRating = petrolStationRating;
        this.fuelQuantity = fuelQuantity;
        this.fuelAmount = fuelAmount;

    }
    public Integer getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(Integer restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public Integer getRestroomRating() {
        return restroomRating;
    }

    public void setRestroomRating(Integer restroomRating) {
        this.restroomRating = restroomRating;
    }

    public Integer getPetrolStationRating() {
        return petrolStationRating;
    }

    public void setPetrolStationRating(Integer petrolStationRating) {
        this.petrolStationRating = petrolStationRating;
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

    public Float getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(Float fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    private Integer restaurantRating;
    private Integer restroomRating;
    private Integer petrolStationRating;
    private Float fuelAmount;
    private Float fuelQuantity;
    private Float foodAmount;

}
