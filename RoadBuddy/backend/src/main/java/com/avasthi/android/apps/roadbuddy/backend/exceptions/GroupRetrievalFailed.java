package com.avasthi.android.apps.roadbuddy.backend.exceptions;

/**
 * Created by vavasthi on 6/12/15.
 */
public class GroupRetrievalFailed extends RuntimeException  {
    public GroupRetrievalFailed(Long id, Throwable ex) {
        super(ex);
        memberId = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    private Long memberId;
}
