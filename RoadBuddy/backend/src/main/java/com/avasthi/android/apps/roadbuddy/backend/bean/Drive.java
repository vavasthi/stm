package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class Drive extends AbstractEntity {
    public Drive() {
        super();
    }

    public Drive(Long eventId, Long groupId, Long memberId) {
        super();
        this.eventId = eventId;
        this.groupId = groupId;
        this.memberId = memberId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Index
    private Long groupId;
    @Index
    private Long eventId;
    @Index
    private Long memberId;
    @Index
    private Date completedAt;
    @Index
    private Boolean done = Boolean.FALSE;
}
