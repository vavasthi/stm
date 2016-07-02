package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 2/10/15.
 */
@Entity
public class KKOrder {

    @Id
    private Long id;
    @Index
    private String orderNumber;
    @Index
    private Long businessId;
    @Index
    private Long userId;
    @Index
    private Long addressId;
    @Index
    private Long timeslotId;
}
