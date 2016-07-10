package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 19/9/15.
 */
@Entity
public class MasterItem {
    public MasterItem(Long businessId,
                      String name,
                      String description,
                      String upc,
                      Boolean fractional,
                      String imageType,
                      String imageCloudKey,
                      String userEmailId,
                      Long taxCategoryId) {

        this.businessId = businessId;
        this.name = name;
        this.description = description;
        this.upc = upc;
        this.fractional = fractional;
        this.imageType = imageType;
        this.imageCloudKey = imageCloudKey;
        this.userEmailId = userEmailId;
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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

    public Boolean getFractional() {
        return fractional;
    }

    public void setFractional(Boolean fractional) {
        this.fractional = fractional;
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


    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    @Override
    public String toString() {
        return "MasterItem{" +
                "businessId=" + businessId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", upc='" + upc + '\'' +
                ", fractional=" + fractional +
                ", imageType='" + imageType + '\'' +
                ", imageCloudKey='" + imageCloudKey + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                ", taxCategoryId=" + taxCategoryId +
                '}';
    }

    @Id
    private Long id;
    @Index
    private Long businessId;
    @Index
    private String name;
    private String description;
    @Index
    private String upc;
    @Index
    private Boolean fractional;
    private String imageType;
    private String imageCloudKey;
    @Index
    private String userEmailId;
    @Index
    private Long taxCategoryId;
}
