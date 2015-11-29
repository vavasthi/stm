package com.avasthi.roadcompanion.data;

/**
 * Created by vavasthi on 26/11/15.
 */
public class FacebookUser {

    public FacebookUser(String id, String name, Boolean administrator) {
        this.administrator = administrator;
        this.id = id;
        this.name = name;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String name;
    private Boolean administrator;
}
