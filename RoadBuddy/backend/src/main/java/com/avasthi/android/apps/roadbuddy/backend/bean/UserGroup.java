package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 6/12/15.
 */
@Entity
public class UserGroup {
    public static final String ADMINISTRATOR = "Administrator";
    public static final String MEMBER = "Member";
    public UserGroup() {

    }
    private UserGroup(Long id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.role = MEMBER;
    }
    public static UserGroup createAdministratorGroup(Long id, String name, String description) {
        UserGroup ug = new UserGroup(id, name, description);
        ug.setRole(ADMINISTRATOR);
        return ug;
    }
    public static UserGroup createMemberGroup(Long id, String name, String description) {
        return new UserGroup(id, name, description);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long id;
    private String name;
    private String description;
    private String role;
}
