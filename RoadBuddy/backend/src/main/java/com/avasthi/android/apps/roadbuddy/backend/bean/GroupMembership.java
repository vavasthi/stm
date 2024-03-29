package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 26/11/15.
 */
@Entity
public class GroupMembership extends AbstractEntity{
    public GroupMembership() {
        super();
    }
    public GroupMembership(Long memberId, Long groupId) {
        super();
        this.groupId = groupId;
        this.memberId = memberId;
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

    @Index
    private Long memberId;
    @Index
    private Long groupId;
}
