package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Set;

/**
 * Created by vavasthi on 3/7/16.
 */
@Entity
public class TaxCategoryEntity {
    public TaxCategoryEntity(String name, Float taxRate, Set<Long> surchargeEntitySet) {
        this.name = name;
        this.taxRate = taxRate;
        this.surchargeEntitySet = surchargeEntitySet;
    }

    public TaxCategoryEntity() {
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

    public Set<Long> getSurchargeEntitySet() {
        return surchargeEntitySet;
    }

    public void setSurchargeEntitySet(Set<Long> surchargeEntitySet) {
        this.surchargeEntitySet = surchargeEntitySet;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxCategoryEntity that = (TaxCategoryEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TaxCategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taxRate=" + taxRate +
                ", surchargeEntitySet=" + surchargeEntitySet +
                '}';
    }

    @Id
    private Long id;
    @Index
    private String name;
    private Float taxRate;
    private Set<Long> surchargeEntitySet;
}
