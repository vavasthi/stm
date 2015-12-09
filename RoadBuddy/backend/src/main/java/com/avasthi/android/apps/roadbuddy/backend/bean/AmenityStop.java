package com.avasthi.android.apps.roadbuddy.backend.bean;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
public class AmenityStop extends AbstractStop {

    public AmenityStop() {

    }
    public AmenityStop(Long userId, Long establishmentId, Date timestamp, Integer restaurantRating, Integer restroomRating, Integer petrolStationRating, Boolean creditCardAccepted) {
        super(userId, establishmentId, timestamp);
        this.restaurantRating = restaurantRating;
        this.restroomRating = restroomRating;
        this.petrolStationRating = petrolStationRating;
        this.creditCardAccepted = creditCardAccepted;

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

    public Boolean getCreditCardAccepted() {
        return creditCardAccepted;
    }

    public void setCreditCardAccepted(Boolean creditCardAccepted) {
        this.creditCardAccepted = creditCardAccepted;
    }

    public Integer getPetrolStationRating() {
        return petrolStationRating;
    }

    public void setPetrolStationRating(Integer petrolStationRating) {
        this.petrolStationRating = petrolStationRating;
    }

    private Integer restaurantRating;
    private Integer restroomRating;
    private Integer petrolStationRating;
    private Boolean creditCardAccepted;
}
