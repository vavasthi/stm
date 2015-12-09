package com.avasthi.android.apps.roadbuddy.backend.bean;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
public class Rating extends AbstractEntity {
    private Rating(Date timestamp, Double latitude, Double longitude, Long userId, Integer rating) {

        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    private Rating() {

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Date timestamp;
    private Double latitude;
    private Double longitude;
    private Long userId;
    private Integer rating;
}
