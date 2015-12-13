package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 26/11/15.
 */
@Entity
public class VehicleOwnership extends AbstractEntity{
    public VehicleOwnership() {
        super();
    }
    public VehicleOwnership(Long memberId, Long vehicleId) {
        super();
        this.vehicleId = vehicleId;
        this.memberId = memberId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Index
    private Long memberId;
    @Index
    private Long vehicleId;
}
