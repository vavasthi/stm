package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 2/10/15.
 */
@Entity
public class TimeSlot {
    @Id
    private Long id;
    @Index
    private Date from;
    @Index
    private Date to;
}
