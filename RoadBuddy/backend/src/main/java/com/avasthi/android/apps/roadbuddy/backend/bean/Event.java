package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class Event extends AbstractEntity{

    private String description;
    @Index
    private Date startDate;
    @Index
    private Date endDate;
    @Index
    private Long city;
}
