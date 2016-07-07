package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 19/9/15.
 */
@Entity
public class MasterItem {
    public MasterItem(String name,
                      String description,
                      String upc,
                      String imageType,
                      String imageCloudKey,
                      String userEmailId,
                      Long measurementCategoryId,
                      Long taxCategoryId) {
        this.name = name;
        this.description = description;
        this.upc = upc;
        this.imageType = imageType;
        this.imageCloudKey = imageCloudKey;
        this.userEmailId = userEmailId;
        this.measurementCategoryId = measurementCategoryId;
        this.taxCategoryId = taxCategoryId;
    }

    public MasterItem() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageCloudKey() {
        return imageCloudKey;
    }

    public void setImageCloudKey(String imageCloudKey) {
        this.imageCloudKey = imageCloudKey;
    }
    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }


    public Long getMeasurementCategoryId() {
        return measurementCategoryId;
    }

    public void setMeasurementCategoryId(Long measurementCategoryId) {
        this.measurementCategoryId = measurementCategoryId;
    }

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    @Override
    public String toString() {
        return "MasterItem{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", upc='" + upc + '\'' +
                ", imageType='" + imageType + '\'' +
                ", imageCloudKey='" + imageCloudKey + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                ", taxCategoryId=" + taxCategoryId +
                ", measurementCategoryId=" + measurementCategoryId +
                '}';
    }

    @Id
    private Long id;
    @Index
    private String name;
    private String description;
    @Index
    private String upc;
    private String imageType;
    private String imageCloudKey;
    @Index
    private String userEmailId;
    @Index
    private Long taxCategoryId;
    @Index
    private Long measurementCategoryId;
}
