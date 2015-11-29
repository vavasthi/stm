package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 22/11/15.
 */
@Entity
public class Group {

    public Group(String name, String facebookGroupId) {
        this.facebookGroupId = facebookGroupId;
        this.name = name;
    }

    public String getFacebookGroupId() {
        return facebookGroupId;
    }

    public void setFacebookGroupId(String facebookGroupId) {
        this.facebookGroupId = facebookGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    private Long id;
    @Index
    private String name;
    @Index
    private String facebookGroupId;
}
