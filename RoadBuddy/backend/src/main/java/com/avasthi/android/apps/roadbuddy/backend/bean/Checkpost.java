package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class Checkpost extends Establishment {
    public Checkpost() {

    }
    public Checkpost(Long userId, Date timestamp, Double latitude, Double longitude, String city, String state, String country) {
        super(userId, timestamp, latitude, longitude, city, state, country);
    }
}
