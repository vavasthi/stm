package com.sanjnan.vitarak.server.backend.entity;

import com.google.appengine.repackaged.com.google.common.base.Flag;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 21/9/15.
 */
@Entity
public class MeasurementUnit {
    public MeasurementUnit() {
    }

    public MeasurementUnit(String businessId, String name, String acronym, Long relatedUnit, Boolean primaryUnit, Double factor) {
        this.name = name;
        this.acronym = acronym;
        this.relatedUnit = relatedUnit;
        this.primaryUnit = primaryUnit;
        this.factor = factor;
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

    public Boolean getPrimaryUnit() {
        return primaryUnit;
    }

    public void setPrimaryUnit(Boolean primaryUnit) {
        this.primaryUnit = primaryUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Long getRelatedUnit() {
        return relatedUnit;
    }

    public void setRelatedUnit(Long relatedUnit) {
        this.relatedUnit = relatedUnit;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    @Id
    private Long id;
    @Index
    private Long businessId;
    @Index
    private String name;
    @Index
    private String acronym;
    @Index
    private Long relatedUnit;
    @Index
    private Boolean primaryUnit;
    private Double factor;
}
