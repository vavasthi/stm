package com.khanakirana.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.sql.Blob;

/**
 * Created by vavasthi on 19/9/15.
 */
@Entity
public class MasterItem {
    public MasterItem(String name, String upc, String imageType, Blob image, String user) {
        this.id = id;
        this.name = name;
        this.upc = upc;
        this.imageType = imageType;
        this.image = image;
        this.user = user;
    }
    public MasterItem() {

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

    public String  getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MasterItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", upc=" + upc +
                ", imageType='" + imageType + '\'' +
                '}';
    }

    @Id
    private String id;
    @Index
    private String name;
    @Index
    private String upc;
    private String imageType;
    private Blob image;
    private String user;
}
