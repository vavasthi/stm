package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 20/12/15.
 */
@Entity
public class Family extends AbstractEntity {
    public Family(Long firstMemberId, Long secondMemberId) {
        this.firstMemberId = firstMemberId;
        this.secondMemberId = secondMemberId;
    }

    public Family() {
    }

    public Long getFirstMemberId() {
        return firstMemberId;
    }

    public void setFirstMemberId(Long firstMemberId) {
        this.firstMemberId = firstMemberId;
    }

    public Long getSecondMemberId() {
        return secondMemberId;
    }

    public void setSecondMemberId(Long secondMemberId) {
        this.secondMemberId = secondMemberId;
    }

    @Index
    private Long firstMemberId;
    @Index
    private Long secondMemberId;
}
