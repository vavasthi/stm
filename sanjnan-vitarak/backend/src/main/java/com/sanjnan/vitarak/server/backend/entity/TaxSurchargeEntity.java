package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 3/7/16.
 */
@Entity
public class TaxSurchargeEntity {
    public TaxSurchargeEntity(String name, Double surchargeRate) {
        this.name = name;
        this.surchargeRate = surchargeRate;
    }

    public TaxSurchargeEntity() {
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

    public Double getSurchargeRate() {
        return surchargeRate;
    }

    public void setSurchargeRate(Double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxSurchargeEntity that = (TaxSurchargeEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TaxSurchargeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surchargeRate=" + surchargeRate +
                '}';
    }

    @Id
    private Long id;
    @Index
    private String name;
    private Double surchargeRate;
}
