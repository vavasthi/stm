package com.khanakirana.backend.entity;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 8/10/15.
 */
@Entity
public class Actionable {

    public Actionable(int actionType, Integer title, Integer actionTitle, Integer description, Long id, String details, Boolean done) {
        this.actionType = actionType;
        this.title = title;
        this.actionTitle = actionTitle;
        this.description = description;
        this.id = id;
        this.details = details;
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

    public Integer getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(Integer actionTitle) {
        this.actionTitle = actionTitle;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Actionable{" +
                "actionType=" + actionType +
                ", title='" + title + '\'' +
                ", actionTitle='" + actionTitle + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }

    private int actionType;
    private Integer title;
    private Integer actionTitle;
    private Integer description;
    private Long id;
    private String details;
    private Boolean done;
}
