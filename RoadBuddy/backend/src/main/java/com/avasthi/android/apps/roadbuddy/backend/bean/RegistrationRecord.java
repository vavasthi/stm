package com.avasthi.android.apps.roadbuddy.backend.bean;

/**
 * Created by vavasthi on 6/12/15.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class RegistrationRecord extends AbstractEntity{
    public RegistrationRecord(Long memberId, String regId) {
        this.memberId = memberId;
        this.regId = regId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public RegistrationRecord() {}

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Index
    private String regId;
    @Index
    private Long memberId;
    // you can add more fields...
}