package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 21/9/15.
 */
@Entity
public class MeasurementCategory {
    public MeasurementCategory() {

    }
    public MeasurementCategory(String name, Boolean fractional) {
        this.name = name;
        this.fractional = fractional;
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

    @Override
    public String toString() {
        return "MeasurementCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementCategory that = (MeasurementCategory) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Boolean getFractional() {
        return fractional;
    }

    public void setFractional(Boolean fractional) {
        this.fractional = fractional;
    }

    @Id
    private Long id;
    @Index
    private String name;
    private Boolean fractional;
}
