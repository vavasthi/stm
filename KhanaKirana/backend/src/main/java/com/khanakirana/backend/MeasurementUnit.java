package com.khanakirana.backend;

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

    public MeasurementUnit(String name, String acronym, MeasurementCategory measurementCategory, Boolean primaryUnit, Double factor) {
        this.name = name;
        this.acronym = acronym;
        this.measurementCategory = measurementCategory;
        this.primaryUnit = primaryUnit;
        this.factor = factor;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public MeasurementCategory getMeasurementCategory() {
        return measurementCategory;
    }

    public void setMeasurementCategory(MeasurementCategory measurementCategory) {
        this.measurementCategory = measurementCategory;
    }

    public Boolean getPrimaryUnit() {
        return primaryUnit;
    }

    public void setPrimaryUnit(Boolean primaryUnit) {
        this.primaryUnit = primaryUnit;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", measurementCategory=" + measurementCategory +
                ", primaryUnit=" + primaryUnit +
                ", factor=" + factor +
                '}';
    }

    @Id
    private Long id;
    @Index
    private String name;
    @Index
    private String acronym;
    @Index
    private MeasurementCategory measurementCategory;
    private Boolean primaryUnit;
    private Double factor;
}
