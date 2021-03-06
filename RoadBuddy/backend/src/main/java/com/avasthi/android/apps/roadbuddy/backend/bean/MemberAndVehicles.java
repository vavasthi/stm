package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class MemberAndVehicles {
    public MemberAndVehicles() {
        super();
    }

    public MemberAndVehicles(Member member, Vehicle[] vehicles, Drive drive) {
        this.member = member;
        this.vehicles = vehicles;
        this.drive = drive;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    private Member member;
    private Vehicle[] vehicles;
    private Drive drive;
}
