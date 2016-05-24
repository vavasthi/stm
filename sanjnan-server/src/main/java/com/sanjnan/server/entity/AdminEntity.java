package com.sanjnan.server.entity;

import javax.persistence.*;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "admins",
        indexes = {
                @Index(name = "admins_email_index",  columnList="email", unique = true),
                @Index(name = "admins_name_index",  columnList="name", unique = true)
        }
)
public class AdminEntity extends BaseEntity {
    public AdminEntity(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }
    public AdminEntity() {

    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private String password;
}
