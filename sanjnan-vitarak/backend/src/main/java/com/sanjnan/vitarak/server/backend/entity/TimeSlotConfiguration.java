package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by vavasthi on 2/10/15.
 */
@Entity
public class TimeSlotConfiguration {


    @Id
    private Long id;
    @Id
    private Long businessId;
    private Date deliveryStartTime;
    private Date deliveryEndTime;
    private Integer slotHours;
    private Integer slotMinutes;
    private Integer slotDeliveries;
}
