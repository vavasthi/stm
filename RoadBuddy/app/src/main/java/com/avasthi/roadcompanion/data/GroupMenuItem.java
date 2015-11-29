package com.avasthi.roadcompanion.data;

/**
 * Created by vavasthi on 29/11/15.
 */
public class GroupMenuItem {
    public GroupMenuItem(String title, String description, String activity) {
        this.title = title;
        this.description = description;
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    private String title;
    private String description;
    private String activity;
}

