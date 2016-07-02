package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 8/10/15.
 */
@Entity
public class Actionable {

    public Actionable(int actionType, Integer title, Integer description, Integer count, Boolean done) {
        this.actionType = actionType;
        this.title = title;
        this.description = description;
        this.count = count;
        this.done = done;
    }


    public Actionable() {
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private int actionType;
    private Integer title;
    private Integer description;
    private Integer count;
    private Boolean done;
}
