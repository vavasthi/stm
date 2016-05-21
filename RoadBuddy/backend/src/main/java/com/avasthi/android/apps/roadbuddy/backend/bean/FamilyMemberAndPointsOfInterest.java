package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 20/12/15.
 */
@Entity
public class FamilyMemberAndPointsOfInterest {
    public FamilyMemberAndPointsOfInterest(Member familyMember, PointsOfInterest pointsOfInterest) {
        this.familyMember = familyMember;
        this.pointsOfInterest = pointsOfInterest;
    }

    public FamilyMemberAndPointsOfInterest() {
    }

    public Member getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(Member familyMember) {
        this.familyMember = familyMember;
    }

    public PointsOfInterest getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(PointsOfInterest pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    private Member familyMember;
    private PointsOfInterest pointsOfInterest;
}
