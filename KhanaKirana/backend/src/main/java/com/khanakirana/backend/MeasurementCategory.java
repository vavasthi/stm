package com.khanakirana.backend;

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
    public MeasurementCategory(String name) {
        this.name = name;
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

    @Id
    private Long id;
    @Index
    private String name;
}
